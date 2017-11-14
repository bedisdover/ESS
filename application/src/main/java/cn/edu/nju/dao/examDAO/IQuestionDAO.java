package cn.edu.nju.dao.examDAO;

import cn.edu.nju.model.examModel.LevelModel;
import cn.edu.nju.model.examModel.QuestionModel;
import cn.edu.nju.info.ResultInfo;

import java.util.List;

public interface IQuestionDAO {

    boolean isMD5Exist(String md5);

    void saveQuestions(List<QuestionModel> questions) throws Exception;

    List<QuestionModel> getAllQuestions(int num);

    void deleteQuestions(int courseId, List<Integer> questionIdList) throws Exception;

    int getCourseIdByQuestionId(int questionId);

    int getLevelNumByCourseId(int courseId);

    int getNumOfQuestions(int courseId, int level);

    List<QuestionModel> getAllQuestionsByCourseId(int courseId);

    QuestionModel getQuestionById(int questionId);
}
