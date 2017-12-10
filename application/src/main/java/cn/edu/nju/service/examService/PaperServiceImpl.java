package cn.edu.nju.service.examService;

import cn.edu.nju.dao.examDAO.*;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.AnsweredItem;
import cn.edu.nju.info.examInfo.AnsweredQuestion;
import cn.edu.nju.model.examModel.ExamModel;
import cn.edu.nju.model.examModel.PaperModel;
import cn.edu.nju.model.examModel.QuestionModel;
import cn.edu.nju.model.examModel.StudentExamModel;
import cn.edu.nju.utils.DateTimeUtil;
import cn.edu.nju.utils.EmailUtil;
import cn.edu.nju.utils.EncryptionUtil;
import cn.edu.nju.utils.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Service(value = "paperService")
public class PaperServiceImpl implements IPaperService {

    private final IExamDAO examDAO;

    private final IPaperDAO paperDAO;

    private final IQuestionDAO questionDAO;

    private final IStudentExamDAO studentExamDAO;

    private final ILevelDAO levelDAO;

    private Map<Integer, List<QuestionModel>> allQuestions;

    private boolean hasGetQuestions;

    @Autowired
    public PaperServiceImpl(IExamDAO examDAO,
                            IPaperDAO paperDAO,
                            IQuestionDAO questionDAO,
                            IStudentExamDAO studentExamDAO,
                            ILevelDAO levelDAO) {
        this.examDAO = examDAO;
        this.paperDAO = paperDAO;
        this.questionDAO = questionDAO;
        this.studentExamDAO = studentExamDAO;
        this.levelDAO = levelDAO;

        allQuestions = new HashMap<>();
        hasGetQuestions = false;
    }

    @Override
    public ResultInfo generatePaper(String key) {
        ResultInfo paramCheckResult = extractKey(key);
        if (!paramCheckResult.isSuccess()) {
            return paramCheckResult;
        }

        StudentExamModel studentExamModel = (StudentExamModel) paramCheckResult.getData();
        int examId = studentExamModel.getExamId();
        String email = studentExamModel.getEmail();
        String password = studentExamModel.getPassword();
        if (!studentExamDAO.doesStudentJoinExam(password, email, examId)) {
            return new ResultInfo(false, "该学生没有参加这场考试", null);
        }

        ExamModel model = examDAO.getExamModelById(examId);
        ResultInfo timeCheckResult = doTimeCheck(model.getStartTime(), model.getEndTime());
        if (!timeCheckResult.isSuccess()) {
            return timeCheckResult;
        }

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
                    QuestionModel.toInfoList(questions, false)
            );
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(ExamServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    public ResultInfo submitPaper(String key, List<AnsweredQuestion> questions) {
        ResultInfo paramCheckResult = extractKey(key);
        if (!paramCheckResult.isSuccess()) {
            return paramCheckResult;
        }

        StudentExamModel studentExamModel = (StudentExamModel) paramCheckResult.getData();
        int examId = studentExamModel.getExamId();
        String email = studentExamModel.getEmail();

        ExamModel model = examDAO.getExamModelById(examId);
        ResultInfo timeCheckResult = doTimeCheck(model.getStartTime(), model.getEndTime());
        if (!timeCheckResult.isSuccess()) {
            return timeCheckResult;
        }

        if (!studentExamDAO.doesStudentJoinExam(
                studentExamModel.getPassword(), email, examId)) {
            return new ResultInfo(false, "该学生没有参加这场考试", null);
        }

        // add paper to database
        int paperId;
        try {
            paperId = paperDAO.addPaper(new PaperModel(
                    0, examId, email,
                    0, 1, createAnswerContent(questions)
            ));
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(PaperServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }

        saveAndInformMark(paperId, examId, email, questions);
        return new ResultInfo(true, "成功提交试卷,考试成绩稍后会发送到您的邮箱", null);
    }

    @Override
    public ResultInfo deletePaper(int paperId) {
        try {
            paperDAO.deletePaperById(paperId);
            return new ResultInfo(true, "成功删除试卷", null);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(ExamServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    private ResultInfo extractKey(String key) {
        String info = EncryptionUtil.base64Decode(key);
        String[] params = info.split("&");
        if (params.length != 3) {
            return new ResultInfo(false, "错误的链接", null);
        }

        String[] emailKeyValue = params[0].split("=");
        if (emailKeyValue.length != 2) {
            return new ResultInfo(false, "错误的链接", null);
        }
        String email = emailKeyValue[1];

        String[] passwordKeyValue = params[1].split("=");
        if (passwordKeyValue.length != 2) {
            return new ResultInfo(false, "错误的链接", null);
        }
        String password = passwordKeyValue[1];

        String[] examIdKeyValue = params[2].split("=");
        if (examIdKeyValue.length != 2) {
            return new ResultInfo(false, "错误的链接", null);
        }
        int examId;
        try {
            examId = Integer.parseInt(examIdKeyValue[1]);
        } catch (NumberFormatException e) {
            return new ResultInfo(false, "错误的链接", null);
        }

        return new ResultInfo(true, null,
                new StudentExamModel(examId, email, password, 1));
    }

    private ResultInfo doTimeCheck(String startTime, String endTime) {
        Date now = new Date();
        Date start, end;
        try {
            start = DateTimeUtil.toDateTime(startTime);
            end = DateTimeUtil.toDateTime(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            Logger.getLogger(PaperServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }

        if (DateTimeUtil.compareDateTime(now, start) < 0) {
            return new ResultInfo(false, "考试还没开始", null);
        }
        if (DateTimeUtil.compareDateTime(now, end) > 0) {
            return new ResultInfo(false, "考试已经结束", null);
        }

        return new ResultInfo(true, null, null);
    }

    private void saveAndInformMark(int paperId, int examId, String email,
                                   List<AnsweredQuestion> answeredQuestions) {
        Runnable task = () -> {
            // calculate and save mark
            double mark = calculateMark(
                    examId, answeredQuestions
            );
            try {
                paperDAO.updateMarkOfPaper(paperId, mark);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.getLogger(PaperServiceImpl.class).error(e);
            }

            // send email to inform student of mark
            ExamModel examModel = examDAO.getExamModelById(examId);
            ResultInfo resultInfo = EmailUtil.sendExamGrade(
                    mark, email, examModel.getName(),
                    examModel.getStartTime(),
                    examModel.getEndTime()
            );
            if (!resultInfo.isSuccess()) {
                Logger.getLogger(PaperServiceImpl.class).error(resultInfo.getMessage());
            }
        };
        new Thread(task).start();
    }

    private double calculateMark(int examId,
                                 List<AnsweredQuestion> answeredQuestions) {
        double mark = 0;
        for (AnsweredQuestion q : answeredQuestions) {
            // assume that questionId must be existed
            int questionId = q.getQuestion().getQuestionId();
            QuestionModel question = questionDAO.getQuestionById(questionId);
            Set<Integer> correctAnswers = extractIntegers(question.getAnswer());
            Set<Integer> studentAnswers = new HashSet<>(q.getAnswer());

            if (isSetEqual(correctAnswers, studentAnswers)) {
                mark += levelDAO.getMarkOfQuestion(
                        examId, question.getCourseId(),
                        question.getLevel()
                );
            }
        }
        return mark;
    }

    private Set<Integer> extractIntegers(String str) {
        String[] integers = str.split(",");
        Set<Integer> result = new HashSet<>();
        for (String s : integers) {
            result.add(Integer.parseInt(s));
        }
        return result;
    }

    private boolean isSetEqual(Set<Integer> set1, Set<Integer> set2) {
        if (set1.size() != set2.size()) return false;
        for (int i : set1) {
            if (!set2.contains(i)) return false;
        }
        return true;
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

    private String createAnswerContent(
            List<AnsweredQuestion> answeredQuestions) throws IOException {
        List<AnsweredItem> answeredItems = AnsweredQuestion.toAnsweredItem(answeredQuestions);
        return JsonUtil.toJson(answeredItems);
    }
}
