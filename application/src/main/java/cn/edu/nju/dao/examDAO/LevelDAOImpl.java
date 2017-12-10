package cn.edu.nju.dao.examDAO;

import cn.edu.nju.dao.DataException;
import cn.edu.nju.mapper.examMapper.LevelMapper;
import cn.edu.nju.model.examModel.LevelModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public LevelDAOImpl(@Qualifier("levelMapper") LevelMapper levelMapper) {
        this.levelMapper = levelMapper;
    }

    @Override
    public void setMarkOfLevel(int courseId, int examId, List<Double> marks) throws Exception {
        levelMapper.setMarkOfLevel(courseId, examId, marks);
    }


    @Override
    public double getMarkOfQuestion(
            int examId, int courseId, int level) throws DataException {
        try {
            return levelMapper.getMarkOfQuestion(examId, courseId, level);
        } catch (Exception e) {
            throw new DataException("该问题不存在");
        }
    }

    @Override
    public List<Double> getMarksOfQuestions(
            int examId, int courseId, List<Integer> levels) throws DataException {
        try {
            return levelMapper.getMarksOfQuestions(examId, courseId, levels);
        } catch (Exception e) {
            throw new DataException("该问题不存在");
        }
    }

    @Override
    public List<LevelModel> getLevelModelList(int courseId) throws DataException {
        try {
            return levelMapper.getLevelModelList(courseId);
        } catch (Exception e) {
            throw new DataException("该课程不存在");
        }
    }

    @Override
    public void addLevelsOfExam(List<LevelModel> levelModelList) throws Exception {
        levelMapper.addLevelsOfExam(levelModelList);
    }
}
