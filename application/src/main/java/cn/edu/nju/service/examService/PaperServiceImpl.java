package cn.edu.nju.service.examService;

import cn.edu.nju.dao.examDAO.*;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.AnsweredPaperInfo;
import cn.edu.nju.info.examInfo.AnsweredQuestion;
import cn.edu.nju.model.examModel.ExamModel;
import cn.edu.nju.model.examModel.PaperModel;
import cn.edu.nju.model.examModel.QuestionModel;
import cn.edu.nju.utils.EmailUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public ResultInfo generatePaper(int examId, String email, String password) {
        if (!studentExamDAO.doesStudentJoinExam(email, examId)) {
            return new ResultInfo(false, "该学生没有参加这场考试", null);
        }

        String examPassword = examDAO.getPasswordByExamId(examId);
        if (!password.equals(examPassword)) {
            return new ResultInfo(false, "考试密码错误", null);
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
                    QuestionModel.toInfoList(questions)
            );
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(ExamServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    public ResultInfo submitPaper(AnsweredPaperInfo paper) {
        // add paper to database
        PaperModel paperModel;
        int paperId;
        try {
            paperModel = paper.toModel();
            paperId = paperDAO.addPaper(paperModel);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(PaperServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }

        saveAndInformMark(
                paperId, paper.getExamId(),
                paper.getStudentEmail(),
                paper.getAnsweredQuestions()
        );
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
            Set<Integer> studentAnswers = new HashSet<>();
            studentAnswers.addAll(q.getAnswer());

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
}
