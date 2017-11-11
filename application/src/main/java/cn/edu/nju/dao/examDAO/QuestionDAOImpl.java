package cn.edu.nju.dao.examDAO;

import cn.edu.nju.mapper.examMapper.QuestionMapper;
import cn.edu.nju.model.examModel.LevelModel;
import cn.edu.nju.model.examModel.QuestionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(value = "questionDAO")
public class QuestionDAOImpl implements IQuestionDAO {

    private final QuestionMapper questionMapper;

    @Autowired
    public QuestionDAOImpl(QuestionMapper questionMapper) {
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
    public List<QuestionModel> getAllQuestions(int num) {
        return questionMapper.getAllQuestions(num);
    }

    @Override
    public void deleteQuestions(int courseId, List<Integer> questionIdList) throws Exception {
        questionMapper.deleteQuestions(courseId, questionIdList);
    }

    @Override
    public int getCourseIdByQuestionId(int questionId) {
        return questionMapper.getCourseIdByQuestionId(questionId);
    }

    @Override
    public void setMarkOfLevel(int courseId, int examId, double[] marks) throws Exception {
        // FIXME: maybe can be optimized with a single sql
        int level = 1;
        for (double mark : marks) {
            questionMapper.setMarkOfLevel(courseId, examId, mark, level);
            level += 1;
        }
    }

    @Override
    public List<LevelModel> getLevelModelList(int courseId) {
        return questionMapper.getLevelModelList(courseId);
    }

    @Override
    public int getLevelNumByCourseId(int courseId) {
        return questionMapper.getLevelNumByCourseId(courseId);
    }

    @Override
    public void updateMarkOfLevelById(List<LevelModel> levelModelList) throws Exception {
        questionMapper.updateMarkOfLevelById(levelModelList);
    }

    @Override
    public void updateMarkOfLevelByUniqueKey(List<LevelModel> levelModelList) throws Exception {
        questionMapper.updateMarkOfLevelByUniqueKey(levelModelList);
    }

    @Override
    public int getNumOfQuestions(int courseId, int level) {
        return questionMapper.getNumOfQuestions(courseId, level);
    }

    @Override
    public double getMarkOfQuestion(int examId, int courseId, int level) {
        return questionMapper.getMarkOfQuestion(examId, courseId, level);
    }

    @Override
    public List<QuestionModel> getAllQuestionsByCourseId(int courseId) {
        return questionMapper.getAllQuestionsByCourseId(courseId);
    }

}
