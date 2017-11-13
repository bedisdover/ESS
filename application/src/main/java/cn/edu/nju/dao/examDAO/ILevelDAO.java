package cn.edu.nju.dao.examDAO;

import cn.edu.nju.model.examModel.LevelModel;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public interface ILevelDAO {

    void setMarkOfLevel(int courseId, int examId, double[] marks) throws Exception;

    void updateMarkOfLevel(List<LevelModel> levelModelList) throws Exception;

    double getMarkOfQuestion(int examId, int courseId, int level);

    List<LevelModel> getLevelModelList(int courseId);

    void addLevelsOfExam(List<LevelModel> levelModelList) throws Exception;
}
