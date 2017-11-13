package cn.edu.nju.service.examService;

import cn.edu.nju.dao.examDAO.IExamDAO;
import cn.edu.nju.dao.examDAO.IPaperDAO;
import cn.edu.nju.dao.examDAO.IQuestionDAO;
import cn.edu.nju.dao.examDAO.IStudentExamDAO;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.model.examModel.ExamModel;
import cn.edu.nju.model.examModel.QuestionModel;
import cn.edu.nju.utils.EncryptionUtil;
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

    private Map<Integer, List<QuestionModel>> allQuestions;

    private boolean hasGetQuestions;

    @Autowired
    public PaperServiceImpl(IExamDAO examDAO,
                            IPaperDAO paperDAO,
                            IQuestionDAO questionDAO,
                            IStudentExamDAO studentExamDAO) {
        this.examDAO = examDAO;
        this.paperDAO = paperDAO;
        this.questionDAO = questionDAO;
        this.studentExamDAO = studentExamDAO;

        allQuestions = new HashMap<>();
        hasGetQuestions = false;
    }

    @Override
    public ResultInfo generatePaper(int examId, String email, String password) {
        String originalEmail = EncryptionUtil.base64Decode(email);
        if (!studentExamDAO.doesStudentJoinExam(originalEmail, examId)) {
            return new ResultInfo(false, "该学生没有参加这场考试", null);
        }

        String originalPassword = EncryptionUtil.base64Decode(password);
        String examPassword = examDAO.getPasswordByExamId(examId);
        if (!originalPassword.equals(examPassword)) {
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
