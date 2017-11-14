package cn.edu.nju.service.examService;

import cn.edu.nju.config.Role;
import cn.edu.nju.dao.courseDAO.IUserCourseDAO;
import cn.edu.nju.dao.examDAO.IExamDAO;
import cn.edu.nju.dao.examDAO.ILevelDAO;
import cn.edu.nju.dao.examDAO.IQuestionDAO;
import cn.edu.nju.dao.examDAO.IStudentExamDAO;
import cn.edu.nju.dao.userDAO.IUserDAO;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.ExamInfo;
import cn.edu.nju.info.examInfo.ExamsOfCourse;
import cn.edu.nju.info.examInfo.StudentInfo;
import cn.edu.nju.model.examModel.ExamModel;
import cn.edu.nju.model.examModel.LevelModel;
import cn.edu.nju.model.userModel.UserModel;
import cn.edu.nju.utils.DateTimeUtil;
import cn.edu.nju.utils.EmailUtil;
import cn.edu.nju.utils.RandomUtil;
import cn.edu.nju.utils.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "examService")
public class ExamServiceImpl implements IExamService {

    private final IUserCourseDAO userCourseDAO;

    private final IQuestionDAO questionDAO;

    private final ILevelDAO levelDAO;

    private final IExamDAO examDAO;

    private final IUserDAO userDAO;

    private final IStudentExamDAO studentExamDAO;


    @Autowired
    public ExamServiceImpl(IUserCourseDAO userCourseDAO,
                           IQuestionDAO questionDAO,
                           ILevelDAO levelDAO,
                           IExamDAO examDAO,
                           IUserDAO userDAO,
                           IStudentExamDAO studentExamDAO) {
        this.userCourseDAO = userCourseDAO;
        this.questionDAO = questionDAO;
        this.levelDAO = levelDAO;
        this.examDAO = examDAO;
        this.userDAO = userDAO;
        this.studentExamDAO = studentExamDAO;
    }

    @Override
    @Transactional
    public ResultInfo createExam(int userId, ExamInfo examInfo) throws Exception {
        ResultInfo checkResult = checkExamInfo(userId, examInfo,
                "只有该门课的老师才能生成该门课的考试");
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        int courseId = examInfo.getCourseId();
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

        // add level setting to database
        List<Double> marks = examInfo.getMarks();
        List<LevelModel> levelModels = new ArrayList<>(marks.size());
        int level = 1;
        for (double mark : marks) {
            levelModels.add(new LevelModel(/* placeholder */0, courseId, level, examId, mark));
            level += 1;
        }
        levelDAO.addLevelsOfExam(levelModels);

        // add student-join-in-exam relationship in database
        // assume that students here are all in t_student
        // this is guaranteed by frontend
        studentExamDAO.joinInExam(examId, extractEmails(examInfo.getStudents()));

        // send email to student, tell them basic information of the exam
        sendNotificationEmail(examInfo);

        return new ResultInfo(true, "成功创建考试", null);

    }

    @Override
    @Transactional
    public ResultInfo updateExam(int userId, ExamInfo examInfo) throws Exception {
        ResultInfo checkResult = checkExamInfo(userId, examInfo,
                "只有该门课的老师才能修改该门课的考试信息");
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        examDAO.updateExam(examInfo.toModel());

        int examId = examInfo.getExamId();
        List<Integer> num = examInfo.getNum();
        examDAO.updateNumOfQuestions(examId,
                StringUtil.stringify(num, ","));

        int level = 1;
        int courseId = examInfo.getCourseId();
        List<Double> marks = examInfo.getMarks();
        List<LevelModel> levelModels = new ArrayList<>(marks.size());
        for (double mark : marks) {
            levelModels.add(new LevelModel(0, courseId, level, examId, mark));
            level += 1;
        }
        levelDAO.updateMarkOfLevel(levelModels);

        return new ResultInfo(true, "成功修改考试信息", null);
    }

    @Override
    @Transactional
    public ResultInfo deleteExam(int userId, int examId) throws Exception {
        ResultInfo permissionResult = checkPermission(
                userId, examDAO.getCourseIdByExamId(examId),
                "只有该门课的老师才能删除该门课的考试信息"
        );
        if (!permissionResult.isSuccess()) {
            return permissionResult;
        }

        examDAO.deleteExam(examId);
        return new ResultInfo(true, "成功删除考试", null);
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
            List<Integer> num = new ArrayList<>(array.length);
            List<Integer> levels = new ArrayList<>(array.length);
            for (int i = 1; i <= array.length; ++i) {
                num.add(Integer.parseInt(array[i - 1]));
                levels.add(i);
            }
            List<Double> marks = levelDAO.getMarksOfQuestions(
                    exam.getExamId(), courseId, levels
            );
            infoList.add(new ExamsOfCourse.ExamInfo(
                    exam.getExamId(), exam.getName(),
                    exam.getPassword(), exam.getStartTime(),
                    exam.getEndTime(), num, marks
            ));
        }

        return new ResultInfo(
                true, "成功获取考试信息列表",
                new ExamsOfCourse(courseId, maxNum, infoList)
        );
    }

    @Override
    public ResultInfo getAllExams(int userId) {
        Role role = userDAO.getRoleById(userId);
        if (role == Role.student) {
            UserModel userInfo = userDAO.getUserInfoById(userId);
            String email = userInfo.getEmail();
            List<ExamModel> examModelList = examDAO.getJoinExam(email);
            return new ResultInfo(true, "成功获取考试信息列表", examModelList);
        } else if (role == Role.teacher) {
            List<Integer> courseIds = userCourseDAO.getCourseIdsByUserId(userId);
            List<ExamModel> examModelList = examDAO.getCreateExam(courseIds);
            return new ResultInfo(true, "成功获取考试信息列表", examModelList);
        } else {
            Logger.getLogger(ExamServiceImpl.class).error("Unexpected role of user");
            return new ResultInfo(false, "系统异常", null);
        }
    }

    private ResultInfo checkExamInfo(int userId, ExamInfo examInfo,
                                     String permissionErrorMsg) {
        if (examInfo.getName() == null) {
            return new ResultInfo(false, "考试名称不能为空", null);
        }

        if (examInfo.getMarks() == null) {
            return new ResultInfo(false, "题目分数列表不能为空", null);
        }

        if (examInfo.getStudents() == null) {
            return new ResultInfo(false, "考试名单不能为空", null);
        }

        int courseId = examInfo.getCourseId();
        ResultInfo permissionResult = checkPermission(
                userId, courseId, permissionErrorMsg
        );
        if (!permissionResult.isSuccess()) {
            return permissionResult;
        }

        // check whether numbers of questions are valid
        ResultInfo numValidResult = isNumValid(examInfo.getNum(), courseId);
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

        return new ResultInfo(true, null, null);
    }

    private ResultInfo checkPermission(int userId, int courseId, String pemissionErrorMsg) {
        if (!userCourseDAO.doesUserHaveCourse(userId, courseId)) {
            return new ResultInfo(false, pemissionErrorMsg, null);
        }
        return new ResultInfo(true, null, null);
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

    private ResultInfo isNumValid(List<Integer> num, int courseId) {
        if (num == null) {
            return new ResultInfo(false, "题目数量不能为空", null);
        }

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
