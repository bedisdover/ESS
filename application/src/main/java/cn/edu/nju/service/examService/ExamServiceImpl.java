package cn.edu.nju.service.examService;

import cn.edu.nju.config.Role;
import cn.edu.nju.dao.DataException;
import cn.edu.nju.dao.courseDAO.ICourseDAO;
import cn.edu.nju.dao.courseDAO.IUserCourseDAO;
import cn.edu.nju.dao.examDAO.*;
import cn.edu.nju.dao.userDAO.IUserDAO;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.*;
import cn.edu.nju.model.examModel.*;
import cn.edu.nju.model.userModel.UserModel;
import cn.edu.nju.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "examService")
public class ExamServiceImpl implements IExamService {

    private final IUserCourseDAO userCourseDAO;

    private final ICourseDAO courseDAO;

    private final IQuestionDAO questionDAO;

    private final ILevelDAO levelDAO;

    private final IExamDAO examDAO;

    private final IUserDAO userDAO;

    private final IStudentExamDAO studentExamDAO;

    private final IStudentDAO studentDAO;

    private final IPaperDAO paperDAO;

    @Autowired
    public ExamServiceImpl(IUserCourseDAO userCourseDAO,
                           ICourseDAO courseDAO,
                           IQuestionDAO questionDAO,
                           ILevelDAO levelDAO,
                           IExamDAO examDAO,
                           IUserDAO userDAO,
                           IStudentExamDAO studentExamDAO,
                           IStudentDAO studentDAO,
                           IPaperDAO paperDAO) {
        this.userCourseDAO = userCourseDAO;
        this.courseDAO = courseDAO;
        this.questionDAO = questionDAO;
        this.levelDAO = levelDAO;
        this.examDAO = examDAO;
        this.userDAO = userDAO;
        this.studentExamDAO = studentExamDAO;
        this.studentDAO = studentDAO;
        this.paperDAO = paperDAO;
    }

    @Override
    @Transactional
    public ResultInfo createExam(int userId, ExamInfoForTeacher examInfo) throws Exception {
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
        int examId = examDAO.createExam(new ExamModel(
                0 /* placeholder */, courseId, 1,
                examInfo.getName(),
                StringUtil.stringify(examInfo.getNum(), ","),
                startTime, endTime
        ));
        examInfo.setExamId(examId);

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
        List<StudentInfo> students = examInfo.getStudents();
        List<StudentExamModel> records = new ArrayList<>(students.size());
        students.forEach(student -> records.add(
                new StudentExamModel(examId, student.getEmail(), RandomUtil.randomString(), 1)
        ));
        studentExamDAO.joinInExam(records);

        // send email to student, tell them basic information of the exam
        sendNotificationEmail(examInfo, records);

        return new ResultInfo(true, "成功创建考试", null);

    }

    @Override
    @Transactional
    public ResultInfo updateExam(int userId, ExamInfoForTeacher examInfo) throws Exception {
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

        int courseId = examInfo.getCourseId();
        levelDAO.setMarkOfLevel(courseId, examId, examInfo.getMarks());

        // delete old records of students joining in the exam
        List<String> oldEmails = studentExamDAO.getExamStudentEmails(examId);
        if (!oldEmails.isEmpty()) {
            studentExamDAO.quitExam(examId, oldEmails);
        }

        // add new records of students joining in the exam
        List<StudentInfo> students = examInfo.getStudents();
        if (!students.isEmpty()) {
            List<StudentExamModel> records = new ArrayList<>(students.size());
            students.forEach(student -> records.add(
                    new StudentExamModel(examId, student.getEmail(), RandomUtil.randomString(), 1)
            ));
            studentExamDAO.joinInExam(records);
        }

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
    public ResultInfo getExamSimpleInfo(int examId) {
        ExamModel model;
        try {
            model = examDAO.getExamModelById(examId);
        } catch (Exception e) {
            return new ResultInfo(false, "考试信息不存在", null);
        }
        return new ResultInfo(true, "成功获得考试信息",
                model.toInfo(null, null, null));
    }

    @Override
    public ResultInfo getExamList(int courseId) {
        List<ExamModel> list;
        try {
            list = examDAO.getExamList(courseId);
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }

        List<Integer> maxNum;
        try {
            maxNum = getQuestionMaxNum(courseId);
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }

        List<ExamsOfCourse.ExamInfo> infoList = new ArrayList<>();
        for (ExamModel exam : list) {
            String[] array = exam.getNum().split(",");
            List<Integer> num = new ArrayList<>(array.length);
            for (int i = 1; i <= array.length; ++i) {
                num.add(Integer.parseInt(array[i - 1]));
            }

            List<Double> marks;
            try {
                marks = getQuestionMarks(exam);
            } catch (DataException e) {
                return new ResultInfo(false, e.getMessage(), null);
            }

            List<StudentModel> studentModelList;
            try {
                studentModelList = studentDAO.getExamStudents(
                        exam.getExamId(), courseId
                );
            } catch (DataException e) {
                return new ResultInfo(false, e.getMessage(), null);
            }

            infoList.add(new ExamsOfCourse.ExamInfo(
                    exam.getExamId(), exam.getName(),
                    exam.getStartTime(),
                    exam.getEndTime(), num, marks,
                    StudentModel.toInfoList(studentModelList)
            ));
        }

        return new ResultInfo(
                true, "成功获取考试信息列表",
                new ExamsOfCourse(courseId, maxNum, infoList)
        );
    }

    @Override
    public ResultInfo getAllExams(int userId) {
        Role role;
        try {
            role = userDAO.getRoleById(userId);
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }

        if (role == Role.student) {
            List<ExamModel> examModelList;
            UserModel userInfo;
            String email;
            try {
                userInfo = userDAO.getUserInfoById(userId);
                email = userInfo.getEmail();
                examModelList = examDAO.getJoinExam(email);
            } catch (DataException e) {
                return new ResultInfo(false, e.getMessage(), null);
            }

            List<ExamInfoForStudent> examInfoList;
            try {
                examInfoList = toExamInfoForStudentList(examModelList, email);
            } catch (DataException e) {
                return new ResultInfo(false, e.getMessage(), null);
            }
            return new ResultInfo(true, "成功获取考试信息列表", examInfoList);
        } else if (role == Role.teacher) {
            List<Integer> courseIds;
            List<ExamModel> examModelList;
            try {
                courseIds = userCourseDAO.getCourseIdsByUserId(userId);
                examModelList = examDAO.getCreateExam(courseIds);
            } catch (Exception e) {
                return new ResultInfo(true, "成功获取考试信息列表",
                        new ArrayList<ExamModel>());
            }

            List<ExamInfoForTeacher> examInfoList;
            try {
                examInfoList = toExamInfoForTeacherList(examModelList);
            } catch (DataException e) {
                return new ResultInfo(false, e.getMessage(), null);
            }

            return new ResultInfo(true, "成功获取考试信息列表", examInfoList);
        } else {
            Logger.getLogger(ExamServiceImpl.class).error("Unexpected role of user");
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    public ResultInfo getExamStatistics(int userId, int examId) {
        int courseId;
        try {
            courseId = examDAO.getCourseIdByExamId(examId);
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }

        ResultInfo permissionResult = checkPermission(
                userId, courseId,
                "只有该门课的老师才能查看该门课的统计信息");
        if (!permissionResult.isSuccess()) {
            return permissionResult;
        }

        List<Double> marks;
        double sum;
        try {
            marks = paperDAO.getStudentMarks(examId);
            sum = calculateTotalMark(examId);
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }

        return new ResultInfo(true, "成功获得统计信息",
                new ExamAnalysis(examId, sum, marks));
    }

    @Override
    public ResultInfo getExamScore(int userId, int examId) {
        int courseId;
        try {
            courseId = examDAO.getCourseIdByExamId(examId);
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }

        ResultInfo permissionResult = checkPermission(
                userId, courseId,
                "只有该门课的老师才能查看该门课的考试结果");
        if (!permissionResult.isSuccess()) {
            return new ResultInfo(false, permissionResult.getMessage(), null);
        }

        List<ExamScoreModel> scores;
        try {
            scores = paperDAO.getStudentScores(examId, courseId);
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }

        return new ResultInfo(true, "成功获得学生成绩", scores);
    }

    @Override
    public ResultInfo getAnsweredPaper(int userId, int examId) {
        UserModel user;
        try {
            user = userDAO.getUserInfoById(userId);
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }
        return getAnsweredPaper(examId, user.getEmail());
    }

    @Override
    public ResultInfo getAnsweredPaper(int userId, int examId, String email) {
        int courseId;
        try {
            courseId = examDAO.getCourseIdByExamId(examId);
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }

        ResultInfo permissionResult = checkPermission(
                userId, courseId,
                "只有该门课的老师才能查看该门考试学生的试卷");
        if (!permissionResult.isSuccess()) {
            return permissionResult;
        }

        return getAnsweredPaper(examId, email);
    }

    @Override
    public String generateExamResultFile(int userId, int examId, String context) {
        int courseId;
        try {
            courseId = examDAO.getCourseIdByExamId(examId);
        } catch (DataException e) {
            Logger.getLogger(ExamServiceImpl.class).error(e.getMessage());
            return null;
        }

        ResultInfo permissionResult = checkPermission(
                userId, courseId,
                "只有该门课的老师才能查看该门课的考试结果");
        if (!permissionResult.isSuccess()) {
            Logger.getLogger(ExamServiceImpl.class).info(permissionResult.getMessage());
            return null;
        }

        String targetName = "score/" + examId + "_" + "scores.xls";
        String fileName = context + targetName;
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                assert file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Logger.getLogger(ExamServiceImpl.class).error(e);
            }
        }

        List<ExamScoreModel> scores;
        try {
            scores = paperDAO.getStudentScores(examId, courseId);
        } catch (DataException e) {
            Logger.getLogger(ExamServiceImpl.class).error(e.getMessage());
            return null;
        }

        boolean success = ExcelUtil.generateScoreFile(file, scores);
        return success ? targetName : null;
    }

    private ResultInfo getAnsweredPaper(int examId, String email) {
        try {
            PaperModel paper = paperDAO.getPaperModel(examId, email);
            if (paper == null) {
                return new ResultInfo(false, "试卷不存在", null);
            }

            List<AnsweredItem> items = JsonUtil.toCollection(
                    paper.getContent(), ArrayList.class, AnsweredItem.class
            );
            List<AnsweredQuestion> questions = new ArrayList<>(items.size());
            for (AnsweredItem item : items) {
                int questionId = item.getQuestionId();
                QuestionModel question = questionDAO.getQuestionById(questionId);
                QuestionInfo info;
                try {
                    info = question.toInfo();
                } catch (IOException e) {
                    e.printStackTrace();
                    Logger.getLogger(ExamServiceImpl.class).error(e);
                    return new ResultInfo(false, "系统异常", null);
                }
                questions.add(new AnsweredQuestion(info, item.createAnswerList()));
            }

            String password = studentExamDAO.getExamPassword(examId, email);
            return new ResultInfo(true, "成功返回试卷信息",
                    paper.toInfo(questions, password, calculateTotalMark(examId)));
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(ExamServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }
    }

    private List<ExamInfoForTeacher> toExamInfoForTeacherList(
            List<ExamModel> examModelList) throws DataException {
        List<ExamInfoForTeacher> examInfoList = new ArrayList<>(examModelList.size());
        for (ExamModel model : examModelList) {
            int courseId = model.getCourseId();
            List<Integer> maxNum = getQuestionMaxNum(courseId);
            List<Double> marks = getQuestionMarks(model);
            List<StudentInfo> students = StudentModel.toInfoList(
                    studentDAO.getExamStudents(model.getExamId(), courseId)
            );
            examInfoList.add(model.toInfo(maxNum, marks, students));
        }
        return examInfoList;
    }

    private List<ExamInfoForStudent> toExamInfoForStudentList(
            List<ExamModel> exams, String email
    ) throws DataException {
        List<ExamInfoForStudent> result = new ArrayList<>(exams.size());
        for (ExamModel exam : exams) {
            int examId = exam.getExamId();
            int courseId = exam.getCourseId();
            String courseName = courseDAO.getCourseNameById(courseId);
            PaperModel paper = paperDAO.getPaperModel(examId, email);
            result.add(new ExamInfoForStudent(
                    examId, exam.getName(),
                    courseName, exam.getStartTime(),
                    exam.getEndTime(), calculateTotalMark(examId),
                    paper == null ? 0 : paper.getMark()
            ));
        }
        return result;
    }

    private double calculateTotalMark(int examId) throws DataException {
        double mark = 0.0;
        ExamModel exam = examDAO.getExamModelById(examId);
        List<Double> marks = getQuestionMarks(exam);
        String[] array = exam.getNum().split(",");
        for (int i = 0; i < array.length; ++i) {
            double m = i == marks.size() ? 0 : marks.get(i);
            mark += Integer.parseInt(array[i]) * m;
        }
        return mark;
    }

    private List<Integer> getQuestionMaxNum(int courseId) throws DataException {
        int levelNum = questionDAO.getLevelNumByCourseId(courseId);
        List<Integer> maxNum = new ArrayList<>(levelNum);
        for (int i = 1; i <= levelNum; ++i) {
            maxNum.add(questionDAO.getNumOfQuestions(courseId, i));
        }
        return maxNum;
    }

    private List<Double> getQuestionMarks(ExamModel exam) throws DataException {
        String[] array = exam.getNum().split(",");
        List<Integer> levels = new ArrayList<>(array.length);
        for (int i = 1; i <= array.length; ++i) {
            levels.add(i);
        }
        return levelDAO.getMarksOfQuestions(
                exam.getExamId(), exam.getCourseId(), levels
        );
    }

    private ResultInfo checkExamInfo(int userId, ExamInfoForTeacher examInfo,
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

    private void sendNotificationEmail(final ExamInfoForTeacher examInfo, final List<StudentExamModel> records) {
        Runnable task = () -> {
            ResultInfo emailResult = EmailUtil.sendExamNotificationEmail(examInfo, records);
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
            return new ResultInfo(false, "考试开始时间格式错误", null);
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
            return new ResultInfo(false, "考试结束时间格式错误", null);
        }

        if (DateTimeUtil.compareDateTime(start, new Date()) <= 0) {
            return new ResultInfo(false, "考试开始时间应该大于当前时间", null);
        }

        if (DateTimeUtil.compareDateTime(start, end) >= 0) {
            return new ResultInfo(false, "考试开始时间应该小于考试结束时间", null);
        }

        return new ResultInfo(true, null, null);
    }

}
