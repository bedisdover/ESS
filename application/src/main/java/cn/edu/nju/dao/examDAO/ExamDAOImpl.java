package cn.edu.nju.dao.examDAO;

import cn.edu.nju.mapper.examMapper.ExamMapper;
import cn.edu.nju.model.examModel.ExamModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("examDAO")
public class ExamDAOImpl implements IExamDAO {

    private final ExamMapper examMapper;

    @Autowired
    public ExamDAOImpl(ExamMapper examMapper) {
        this.examMapper = examMapper;
    }

    @Override
    public int createExam(ExamModel model) throws Exception {
        examMapper.createExam(model);
        return model.getExamId();
    }

    @Override
    public void deleteExam(int examId) throws Exception {
        examMapper.deleteExam(examId);
    }

    @Override
    public void updateExam(ExamModel model) throws Exception {
        examMapper.updateExam(model);
    }

    @Override
    public void updateNumOfQuestions(int examId, String num) throws Exception {
        examMapper.updateNumOfQuestions(examId, num);
    }

    @Override
    public int getCourseIdByExamId(int examId) {
        return examMapper.getCourseIdByExamId(examId);
    }

    @Override
    public String getPasswordByExamId(int examId) {
        return examMapper.getPasswordByExamId(examId);
    }

    @Override
    public List<ExamModel> getExamList(int courseId) {
        return roundTime(examMapper.getExamList(courseId));
    }

    @Override
    public List<ExamModel> getJoinExam(String email) {
        return roundTime(examMapper.getJoinExam(email));
    }

    @Override
    public List<ExamModel> getCreateExam(List<Integer> courseIdList) {
        return roundTime(examMapper.getCreateExam(courseIdList));
    }

    @Override
    public ExamModel getExamModelById(int examId) {
        return roundTime(examMapper.getExamModelById(examId));
    }

    private ExamModel roundTime(ExamModel model) {
        model.setStartTime(model.getStartTime().split("\\.")[0]);
        model.setEndTime(model.getEndTime().split("\\.")[0]);
        return model;
    }

    private List<ExamModel> roundTime(List<ExamModel> models) {
        models.forEach(this::roundTime);
        return models;
    }

}
