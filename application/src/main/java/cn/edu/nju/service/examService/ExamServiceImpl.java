package cn.edu.nju.service.examService;

import cn.edu.nju.dao.courseDAO.IUserCourseDAO;
import cn.edu.nju.dao.examDAO.IExamDAO;
import cn.edu.nju.dao.examDAO.IQuestionDAO;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.ExamInfo;
import cn.edu.nju.info.examInfo.ExamsOfCourse;
import cn.edu.nju.info.userInfo.StudentInfo;
import cn.edu.nju.model.examModel.ExamModel;
import cn.edu.nju.model.examModel.LevelModel;
import cn.edu.nju.model.examModel.QuestionModel;
import cn.edu.nju.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

@Service(value = "examService")
public class ExamServiceImpl implements IExamService {

    private final IUserCourseDAO userCourseDAO;

    private final IQuestionDAO questionDAO;

    private final IExamDAO examDAO;

    private Map<Integer, List<QuestionModel>> allQuestions;

    private boolean hasGetQuestions;

    @Autowired
    public ExamServiceImpl(IUserCourseDAO userCourseDAO,
                           IQuestionDAO questionDAO,
                           IExamDAO examDAO) {
        this.userCourseDAO = userCourseDAO;
        this.questionDAO = questionDAO;
        this.examDAO = examDAO;

        allQuestions = new HashMap<>();
        hasGetQuestions = false;
    }

    @Override
    @Transactional
    public ResultInfo createExam(int userId, ExamInfo examInfo, InputStream students) throws Exception {
        int courseId = examInfo.getCourseId();
        if (!userCourseDAO.doesUserHaveCourse(userId, courseId)) {
            return new ResultInfo(
                    false, "只有该门课的老师才能生成该门课的考试", null
            );
        }

        // check whether numbers of questions are valid
        List<Integer> num = examInfo.getNum();
        ResultInfo numValidResult = isNumValid(num,courseId);
        if (!numValidResult.isSuccess()) {
            return numValidResult;
        }

        // check whether start time and end time are valid
        String startTime = examInfo.getStartTime();
        String endTime = examInfo.getEndTime();
        ResultInfo timeValidResult = areTimeValid(startTime, endTime);
        if (!timeValidResult.isSuccess()) {
            return timeValidResult;
        }

        // add an exam record to database
        String examPassword = RandomUtil.randomString();
        examInfo.setPassword(examPassword);
        int examId = examDAO.createExam(new ExamModel(
                0 /* placeholder */, courseId, 1,
                examInfo.getName(), examPassword,
                numListToString(examInfo.getNum()),
                startTime, endTime
        ));

        // if students are not null, update student list of the exam
        if (students != null) {
            String md5Value = EncryptionUtil.md5(students);
            if (!examDAO.isStudentFileMD5Exist(md5Value)) {
                List<StudentInfo> studentList = ExcelUtil.extractStudents(
                        examInfo.getCourseId(), students
                );
                examDAO.updateExamStudents(examId, StudentInfo.toModelList(studentList, md5Value));
            }
        }

        // add level setting to database
        List<Double> marks = examInfo.getMarks();
        List<LevelModel> levelModels = new ArrayList<>(marks.size());
        int level = 1;
        for (double mark : marks) {
            levelModels.add(new LevelModel(/* placeholder */0, courseId, level, examId, mark));
            level += 1;
        }
        questionDAO.addLevelsOfExam(levelModels);

        // add student-join-in-exam relationship in database
        examDAO.joinInExam(examId, extractEmails(examInfo.getStudents()));

        // send email to student, tell them basic information of the exam
        sendNotificationEmail(examInfo);

        return new ResultInfo(true, "成功创建考试", null);

    }

    @Override
    public ResultInfo updateExam(int userId, int examId, String num, String mark) {
        int courseId = examDAO.getCourseIdByExamId(examId);
        if (!userCourseDAO.doesUserHaveCourse(userId, courseId)) {
            return new ResultInfo(
                    false, "只有该门课的老师才能修改该门课的考试信息", null
            );
        }

//        ResultInfo numValidResult = isNumValid(num,courseId);
//        if (!numValidResult.isSuccess()) {
//            return numValidResult;
//        }

        try {
            examDAO.updateNumOfQuestions(examId, num);

            int level = 1;
            String[] marks = mark.split(",");
            List<LevelModel> levelModels = new ArrayList<>(marks.length);
            for (String str : marks) {
                double m = Double.parseDouble(str);
                levelModels.add(new LevelModel(0, courseId, level, examId, m));
                level += 1;
            }
            questionDAO.addLevelsOfExam(levelModels);
            return new ResultInfo(true, "成功修改考试信息", null);
        }
        catch (NumberFormatException e) {
            return new ResultInfo(
                    false, "等级分数应该是由逗号隔开的小数组成", null
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(ExamServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    public ResultInfo getExamList(int courseId) {
        List<ExamModel> list = examDAO.getExamList(courseId);

        int levelNum = questionDAO.getLevelNumByCourseId(courseId);
        List<Integer> maxNum = new ArrayList<>(levelNum);
        for (int i = 1; i <= levelNum; ++i) {
            maxNum.add(questionDAO.getNumOfQuestions(courseId, i));
        }

        List<ExamsOfCourse.ExamInfo> infoList = new ArrayList<>();
        for (ExamModel exam : list) {
            String[] array = exam.getNum().split(",");
            List<Double> marks = new ArrayList<>(array.length);
            List<Integer> num = new ArrayList<>(array.length);
            for (int i = 1; i <= array.length; ++i) {
                marks.add(questionDAO.getMarkOfQuestion(
                        exam.getExamId(), courseId, i
                ));
                num.add(Integer.parseInt(array[i - 1]));
            }
            infoList.add(new ExamsOfCourse.ExamInfo(
                    exam.getExamId(), num, marks
            ));
        }

        return new ResultInfo(
                true, "成功获取考试信息列表",
                new ExamsOfCourse(courseId, maxNum, infoList)
        );
    }

    @Override
    public ResultInfo generatePaper(int examId) {
        ExamModel examModel = examDAO.getExamModelById(examId);
        int courseId = examModel.getCourseId();
        String[] numArray = examModel.getNum().split(",");
        List<Integer> numList = new ArrayList<>(numArray.length);
        for (String str : numArray) {
            numList.add(Integer.parseInt(str));
        }

        if (!hasGetQuestions) {
            synchronized (this) {
                if (!hasGetQuestions) {
                    hasGetQuestions = true;
                    initQuestionMap(courseId);
                }
            }
        }

        List<QuestionModel> questions = generateQuestions(numList);
        try {
            return new ResultInfo(true, "成功生成试卷",
                    QuestionModel.toInfoList(questions)
            );
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(ExamServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    public ResultInfo deletePaper(int paperId) {
        try {
            examDAO.deletePaperById(paperId);
            return new ResultInfo(true, "成功删除试卷", null);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(ExamServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    private void sendNotificationEmail(final ExamInfo examInfo) {
        Runnable task = () -> {
            ResultInfo emailResult = EmailUtil.sendExamNotificationEmail(examInfo);
            if (!emailResult.isSuccess()) {
                Logger.getLogger(ExamServiceImpl.class).error("Fail to send email of exam notification");
            }
        };
        new Thread(task).start();
    }

    private void initQuestionMap(int courseId) {
        List<QuestionModel> questions = questionDAO.getAllQuestionsByCourseId(courseId);
        for (QuestionModel q : questions) {
            int level = q.getLevel();
            allQuestions.computeIfAbsent(level, lv -> new ArrayList<>());
            allQuestions.get(level).add(q);
        }
    }

    private List<QuestionModel> generateQuestions(List<Integer> numList) {
        List<QuestionModel> result = new ArrayList<>();
        int size = numList.size();
        for (int level = 1; level <= size; ++level) {
            List<QuestionModel> list = allQuestions.get(level);
            List<Integer> index = new ArrayList<>();
            for (int i = 0; i < list.size(); ++i) {
                index.add(i);
            }
            Collections.shuffle(index);
            int num = numList.get(level - 1);
            for (int i = 0; i < num; ++i) {
                result.add(list.get(index.get(i)));
            }
        }
        return result;
    }

    private ResultInfo isNumValid(List<Integer> num, int courseId) {
        try {
            int level = 1;
            for (int n : num) {
                if (n < 0) throw new NumberFormatException();
                int maxNum = questionDAO.getNumOfQuestions(courseId, level);
                if (n > maxNum) {
                    return new ResultInfo(
                            false, "题目数量不能超过题库的总数", null
                    );
                }
                level += 1;
            }
            return new ResultInfo(true, "数目正确", null);
        }
        catch (NumberFormatException e) {
            return new ResultInfo(false, "每道题的数目应该为非负数", null);
        }
    }

    private ResultInfo areTimeValid(String startTime, String endTime) {
        if (startTime == null) {
            return new ResultInfo(false, "考试开始时间不能为空", null);
        }

        Date start;
        try {
            start = DateTimeUtil.toDateTime(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
            Logger.getLogger(ExamServiceImpl.class).error(e);
            return new ResultInfo(false, "开始考试时间格式错误", null);
        }

        if (endTime == null) {
            return new ResultInfo(false, "考试结束时间不能为空", null);
        }

        Date end;
        try {
            end = DateTimeUtil.toDateTime(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            Logger.getLogger(ExamServiceImpl.class).error(e);
            return new ResultInfo(false, "结束考试时间格式错误", null);
        }

        if (DateTimeUtil.compareDateTime(start, new Date()) <= 0) {
            return new ResultInfo(false, "考试考试时间应该大于当前时间", null);
        }

        if (DateTimeUtil.compareDateTime(start, end) >= 0) {
            return new ResultInfo(false, "开始考试时间应该小于结束考试时间", null);
        }

        return new ResultInfo(true, null, null);
    }

    private String numListToString(List<Integer> num) {
        StringBuilder builder = new StringBuilder(num.size() << 1);
        int size = num.size();
        if (size == 0) return "";
        for (int i = 0; i < size - 1; ++i) {
            builder.append(num.get(i)).append(",");
        }
        builder.append(num.get(size - 1));
        return builder.toString();
    }

    private List<String> extractEmails(List<StudentInfo> students) {
        int size = students.size();
        if (size == 0) return new ArrayList<>();
        List<String> emails = new ArrayList<>(size);
        students.forEach((info) -> emails.add(info.getEmail()));
        return emails;
    }

}
