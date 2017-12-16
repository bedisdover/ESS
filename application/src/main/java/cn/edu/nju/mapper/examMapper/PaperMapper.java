package cn.edu.nju.mapper.examMapper;

import cn.edu.nju.model.examModel.ExamScoreModel;
import cn.edu.nju.model.examModel.PaperModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Service(value = "paperMapper")
public interface PaperMapper {

    void deletePaperById(
            @Param("paperId") int paperId) throws Exception;

    void addPaper(
            @Param("paper")PaperModel paper) throws Exception;

    void updateMarkOfPaper(
            @Param("paperId") int paperId,
            @Param("mark") double mark) throws Exception;
    
    int getPaperNum(
            @Param("examId") int examId,
            @Param("email") String email);

    PaperModel getPaperModel(
            @Param("examId") int examId,
            @Param("email") String email) throws Exception;

    List<Double> getStudentMarks(
            @Param("examId") int examId) throws Exception;

    List<ExamScoreModel> getStudentScores(
            @Param("examId") int examId,
            @Param("courseId") int courseId) throws Exception;
}
