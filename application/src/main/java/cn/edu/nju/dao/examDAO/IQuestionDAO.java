package cn.edu.nju.dao.examDAO;

import cn.edu.nju.model.examModel.QuestionModel;
import cn.edu.nju.info.ResultInfo;

import java.util.List;

public interface IQuestionDAO {

    boolean isMD5Exist(String md5);

    ResultInfo saveQuestions(List<QuestionModel> questions);

    ResultInfo getAllQuestions(int num);

    ResultInfo deleteQuestions(List<Integer> questionIdList);

    int getCourseIdByQuestionId(int questionId);
}
