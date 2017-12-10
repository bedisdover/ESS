package cn.edu.nju.mapper.examMapper;

import cn.edu.nju.model.examModel.LevelModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Service(value = "levelMapper")
public interface LevelMapper {

    void setMarkOfLevel(
            @Param("courseId") int courseId,
            @Param("examId") int examId,
            @Param("marks") List<Double> marks) throws Exception;

    double getMarkOfQuestion(
            @Param("examId") int examId,
            @Param("courseId") int courseId,
            @Param("level") int level) throws Exception;

    List<Double> getMarksOfQuestions(
            @Param("examId") int examId,
            @Param("courseId") int courseId,
            @Param("levels") List<Integer> levels) throws Exception;

    List<LevelModel> getLevelModelList(
            @Param("courseId") int courseId) throws Exception;

    void addLevelsOfExam(
            @Param("levelModelList") List<LevelModel> levelModelList) throws Exception;
}
