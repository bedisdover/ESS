package cn.edu.nju.dao.examDAO;

import cn.edu.nju.mapper.examMapper.ExamMapper;
import cn.edu.nju.model.examModel.ExamModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("examDAO")
public class ExamDAOImpl implements IExamDAO {

    private final ExamMapper examMapper;

    @Autowired
    public ExamDAOImpl(ExamMapper examMapper) {
        this.examMapper = examMapper;
    }

    @Transactional
    @Override
    public int createExam(ExamModel model) throws Exception {
        examMapper.createExam(model);
        return model.getExamId();
    }

    @Transactional
    @Override
    public void deleteExam(int examId) throws Exception {
        examMapper.deleteExam(examId);
    }

    @Override
    public int getCourseIdByExamId(int examId) {
        return examMapper.getCourseIdByExamId(examId);
    }

    @Override
    public void updateNumOfQuestions(int examId, String num) throws Exception {
        examMapper.updateNumOfQuestions(examId, num);
    }

    @Override
    public List<ExamModel> getExamList(int courseId) {
        return examMapper.getExamList(courseId);
    }
}
