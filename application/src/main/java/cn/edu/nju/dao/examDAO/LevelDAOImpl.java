package cn.edu.nju.dao.examDAO;

import cn.edu.nju.mapper.examMapper.LevelMapper;
import cn.edu.nju.model.examModel.LevelModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Service("levelDAO")
public class LevelDAOImpl implements ILevelDAO {

    private final LevelMapper levelMapper;

    @Autowired
    public LevelDAOImpl(LevelMapper levelMapper) {
        this.levelMapper = levelMapper;
    }

    @Override
    public void setMarkOfLevel(int courseId, int examId, double[] marks) throws Exception {
        // FIXME: maybe can be optimized with a single sql
        int level = 1;
        for (double mark : marks) {
            levelMapper.setMarkOfLevel(courseId, examId, mark, level);
            level += 1;
        }
    }

    @Override
    public void updateMarkOfLevel(List<LevelModel> levelModelList) throws Exception {
        levelMapper.updateMarkOfLevel(levelModelList);
    }

    @Override
    public double getMarkOfQuestion(int examId, int courseId, int level) {
        return levelMapper.getMarkOfQuestion(examId, courseId, level);
    }

    @Override
    public List<Double> getMarksOfQuestions(int examId, int courseId, List<Integer> levels) {
        return levelMapper.getMarksOfQuestions(examId, courseId, levels);
    }

    @Override
    public List<LevelModel> getLevelModelList(int courseId) {
        return levelMapper.getLevelModelList(courseId);
    }

    @Override
    public void addLevelsOfExam(List<LevelModel> levelModelList) throws Exception {
        levelMapper.addLevelsOfExam(levelModelList);
    }
}
