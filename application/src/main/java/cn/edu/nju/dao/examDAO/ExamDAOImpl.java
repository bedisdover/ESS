package cn.edu.nju.dao.examDAO;

import cn.edu.nju.mapper.examMapper.ExamMapper;
import cn.edu.nju.model.examModel.ExamModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
