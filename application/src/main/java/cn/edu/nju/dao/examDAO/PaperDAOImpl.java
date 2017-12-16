package cn.edu.nju.dao.examDAO;

import cn.edu.nju.dao.DataException;
import cn.edu.nju.mapper.examMapper.PaperMapper;
import cn.edu.nju.model.examModel.ExamScoreModel;
import cn.edu.nju.model.examModel.PaperModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Service(value = "paperDAO")
public class PaperDAOImpl implements IPaperDAO {

    private final PaperMapper paperMapper;

    @Autowired
    public PaperDAOImpl(@Qualifier("paperMapper") PaperMapper paperMapper) {
        this.paperMapper = paperMapper;
    }

    @Override
    public void deletePaperById(int paperId) throws Exception {
        paperMapper.deletePaperById(paperId);
    }

    @Override
    public int addPaper(PaperModel paper) throws Exception {
        paperMapper.addPaper(paper);
        return paper.getPaperId();
    }

    @Override
    public void updateMarkOfPaper(int paperId, double mark) throws Exception {
        paperMapper.updateMarkOfPaper(paperId, mark);
    }

    @Override
    public boolean doesSubmitPaper(int examId, String email) {
        return paperMapper.getPaperNum(examId, email) > 0;
    }

    @Override
    public PaperModel getPaperModel(
            int examId, String email) throws DataException {
        try {
            return paperMapper.getPaperModel(examId, email);
        } catch (Exception e) {
            throw new DataException("该试卷不存在");
        }
    }

    @Override
    public List<Double> getStudentMarks(int examId) throws DataException {
        try {
            return paperMapper.getStudentMarks(examId);
        } catch (Exception e) {
            throw new DataException("该考试不存在");
        }
    }

    @Override
    public List<ExamScoreModel> getStudentScores(
            int examId, int courseId) throws DataException {
        try {
            return paperMapper.getStudentScores(examId, courseId);
        } catch (Exception e) {
            throw new DataException("该考试不存在");
        }
    }
}
