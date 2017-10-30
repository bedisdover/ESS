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

    void setMarkOfLevel(int courseId, int examId, double[] marks) throws Exception;

    List<LevelModel> getLevelModelList(int courseId);

    void updateMarkOfLevelById(List<LevelModel> levelModelList) throws Exception;

    void updateMarkOfLevelByUniqueKey(List<LevelModel> levelModelList) throws Exception;

    int getNumOfQuestions(int courseId, int level);
}
