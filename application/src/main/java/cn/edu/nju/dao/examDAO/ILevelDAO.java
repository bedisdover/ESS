package cn.edu.nju.dao.examDAO;

import cn.edu.nju.model.examModel.LevelModel;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public interface ILevelDAO {

    void setMarkOfLevel(int courseId, int examId, List<Double> marks) throws Exception;

    double getMarkOfQuestion(int examId, int courseId, int level);

    List<Double> getMarksOfQuestions(int examId, int courseId, List<Integer> levels);

    List<LevelModel> getLevelModelList(int courseId);

    void addLevelsOfExam(List<LevelModel> levelModelList) throws Exception;
}
