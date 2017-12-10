package cn.edu.nju.dao.examDAO;

import cn.edu.nju.dao.DataException;
import cn.edu.nju.mapper.examMapper.ExamMapper;
import cn.edu.nju.model.examModel.ExamModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("examDAO")
public class ExamDAOImpl implements IExamDAO {

    private final ExamMapper examMapper;

    @Autowired
    public ExamDAOImpl(@Qualifier("examMapper") ExamMapper examMapper) {
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
    public int getCourseIdByExamId(int examId) throws DataException {
        try {
            return examMapper.getCourseIdByExamId(examId);
        } catch (Exception e) {
            throw new DataException("该考试不存在");
        }
    }

    @Override
    public List<ExamModel> getExamList(int courseId) throws DataException {
        try {
            return roundTime(examMapper.getExamList(courseId));
        } catch (Exception e) {
            throw new DataException("该课程不存在");
        }
    }

    @Override
    public List<ExamModel> getJoinExam(String email) throws DataException {
        try {
            return roundTime(examMapper.getJoinExam(email));
        } catch (Exception e) {
            throw new DataException("该邮箱不存在");
        }
    }

    @Override
    public List<ExamModel> getCreateExam(List<Integer> courseIdList) throws DataException {
        try {
            return roundTime(examMapper.getCreateExam(courseIdList));
        } catch (Exception e) {
            throw new DataException("该课程不存在");
        }
    }

    @Override
    public ExamModel getExamModelById(int examId) throws DataException {
        try {
            return roundTime(examMapper.getExamModelById(examId));
        } catch (Exception e) {
            throw new DataException("该考试不存在");
        }
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
