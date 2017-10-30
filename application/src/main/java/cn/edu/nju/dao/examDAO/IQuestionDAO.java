package cn.edu.nju.dao.examDAO;

import cn.edu.nju.model.examModel.LevelModel;
import cn.edu.nju.model.examModel.QuestionModel;
import cn.edu.nju.info.ResultInfo;

import java.util.List;

public interface IQuestionDAO {

    boolean isMD5Exist(String md5);

    ResultInfo saveQuestions(List<QuestionModel> questions);

    ResultInfo getAllQuestions(int num);

    ResultInfo deleteQuestions(int courseId, List<Integer> questionIdList);

    int getCourseIdByQuestionId(int questionId);

    ResultInfo setMarkOfLevel(int courseId, int examId, double[] marks);

    ResultInfo getLevelModelList(int courseId);

    ResultInfo updateMarkOfLevelById(List<LevelModel> levelModelList);

    ResultInfo updateMarkOfLevelByUniqueKey(List<LevelModel> levelModelList);
}
