package cn.edu.nju.dao.examDAO;

import cn.edu.nju.model.examModel.ExamScoreModel;
import cn.edu.nju.model.examModel.PaperModel;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public interface IPaperDAO {

    void deletePaperById(int paperId) throws Exception;

    // return paper id
    int addPaper(PaperModel paper) throws Exception;

    void updateMarkOfPaper(int paperId, double mark) throws Exception;

    List<Double> getStudentMarks(int examId);

    List<ExamScoreModel> getStudentScores(int examId);

}
