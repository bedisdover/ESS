package cn.edu.nju.dao.examDAO;

import cn.edu.nju.dao.SessionFactory;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.mapper.examMapper.QuestionMapper;
import cn.edu.nju.model.examModel.LevelModel;
import cn.edu.nju.model.examModel.QuestionModel;
import cn.edu.nju.service.examService.QuestionServiceImpl;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "questionDAO")
public class QuestionDAOImpl implements IQuestionDAO {
    @Override
    public boolean isMD5Exist(String md5) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            QuestionMapper mapper = session.getMapper(QuestionMapper.class);
            return mapper.getMD5Num(md5) > 0;
        }
    }

    @Override
    public ResultInfo saveQuestions(List<QuestionModel> questions) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            QuestionMapper mapper = session.getMapper(QuestionMapper.class);
            mapper.addQuestionList(questions);
            return new ResultInfo(true, "成功添加问题", null);
        }
        catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(QuestionServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    public ResultInfo getAllQuestions(int num) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            QuestionMapper mapper = session.getMapper(QuestionMapper.class);
            List<QuestionModel> questions = mapper.getAllQuestions(num);
            return new ResultInfo(true, "成功添加问题", questions);
        }
        catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(QuestionServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    public ResultInfo deleteQuestions(int courseId, List<Integer> questionIdList) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            QuestionMapper mapper = session.getMapper(QuestionMapper.class);
            mapper.deleteQuestions(courseId, questionIdList);
            return new ResultInfo(true, "成功删除问题", null);
        }
        catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(QuestionServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    public int getCourseIdByQuestionId(int questionId) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            QuestionMapper mapper = session.getMapper(QuestionMapper.class);
            return mapper.getCourseIdByQuestionId(questionId);
        }
    }

    @Override
    public ResultInfo setMarkOfLevel(int courseId, int examId, double[] marks) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            QuestionMapper mapper = session.getMapper(QuestionMapper.class);
            // FIXME: maybe can be optimized with a single sql
            int level = 1;
            for (double mark : marks) {
                mapper.setMarkOfLevel(courseId, examId, mark, level);
                level += 1;
            }
            return new ResultInfo(true, "成功设置等级对应的分数", null);
        }
        catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(QuestionServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    public ResultInfo getLevelModelList(int courseId) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            QuestionMapper mapper = session.getMapper(QuestionMapper.class);
            List<LevelModel> list = mapper.getLevelModelList(courseId);
            return new ResultInfo(true, "成功获得等级信息列表", list);
        }
        catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(QuestionServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    public ResultInfo updateMarkOfLevel(List<LevelModel> levelModelList) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            QuestionMapper mapper = session.getMapper(QuestionMapper.class);
            mapper.updateMarkOfLevel(levelModelList);
            return new ResultInfo(true, "成功更新等级分数", null);
        }
        catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(QuestionServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }
}
