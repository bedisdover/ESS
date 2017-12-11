package cn.edu.nju.dao.examDAO;

import cn.edu.nju.dao.DataException;
import cn.edu.nju.mapper.examMapper.QuestionMapper;
import cn.edu.nju.model.examModel.QuestionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(value = "questionDAO")
public class QuestionDAOImpl implements IQuestionDAO {

    private final QuestionMapper questionMapper;

    @Autowired
    public QuestionDAOImpl(@Qualifier("questionMapper") QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Override
    public boolean isMD5Exist(String md5) {
        return questionMapper.getMD5Num(md5) > 0;
    }

    @Override
    public void saveQuestions(List<QuestionModel> questions) throws Exception {
        questionMapper.addQuestionList(questions);
    }

    @Override
    public List<QuestionModel> getCourseQuestions(
            int courseId, int offset, int size) throws DataException {
        try {
            return questionMapper.getCourseQuestions(courseId, offset, size);
        } catch (Exception e) {
            throw new DataException("该课程不存在");
        }
    }

    @Override
    public void deleteQuestions(int courseId, List<Integer> questionIdList) throws Exception {
        questionMapper.deleteQuestions(courseId, questionIdList);
    }

    @Override
    public int getCourseIdByQuestionId(
            int questionId) throws DataException {
        try {
            return questionMapper.getCourseIdByQuestionId(questionId);
        } catch (Exception e) {
            throw new DataException("该问题不存在");
        }
    }

    @Override
    public int getLevelNumByCourseId(
            int courseId) throws DataException {
        try {
            return questionMapper.getLevelNumByCourseId(courseId);
        } catch (Exception e) {
            throw new DataException("该课程不存在");
        }
    }

    @Override
    public int getNumOfQuestions(int courseId, int level) {
        return questionMapper.getNumOfQuestions(courseId, level);
    }

    @Override
    public int getNumOfCourseQuestions(int courseId) {
        return questionMapper.getNumOfCourseQuestions(courseId);
    }

    @Override
    public double getTotalMarkOfQuestions(
            int examId, List<Integer> questionIds) throws DataException {
        try {
            return questionMapper.getTotalMarkOfQuestions(examId, questionIds);
        } catch (Exception e) {
            throw new DataException("问题不存在");
        }
    }

    @Override
    public List<QuestionModel> getAllQuestionsByCourseId(
            int courseId) throws DataException {
        try {
            return questionMapper.getAllQuestionsByCourseId(courseId);
        } catch (Exception e) {
            throw new DataException("课程不存在");
        }
    }

    @Override
    public QuestionModel getQuestionById(
            int questionId) throws DataException {
        try {
            return questionMapper.getQuestionById(questionId);
        } catch (Exception e) {
            throw new DataException("问题不存在");
        }
    }

}
