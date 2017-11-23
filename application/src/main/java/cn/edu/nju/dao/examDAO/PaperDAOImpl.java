package cn.edu.nju.dao.examDAO;

import cn.edu.nju.mapper.examMapper.PaperMapper;
import cn.edu.nju.model.examModel.ExamScoreModel;
import cn.edu.nju.model.examModel.PaperModel;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PaperDAOImpl(PaperMapper paperMapper) {
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
    public PaperModel getPaperModel(int examId, String email) {
        return paperMapper.getPaperModel(examId, email);
    }

    @Override
    public List<Double> getStudentMarks(int examId) {
        return paperMapper.getStudentMarks(examId);
    }

    @Override
    public List<ExamScoreModel> getStudentScores(int examId) {
        return paperMapper.getStudentScores(examId);
    }
}
