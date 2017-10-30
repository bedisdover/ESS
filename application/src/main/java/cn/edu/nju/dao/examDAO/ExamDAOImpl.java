package cn.edu.nju.dao.examDAO;

import cn.edu.nju.dao.SessionFactory;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.mapper.examMapper.ExamMapper;
import cn.edu.nju.model.examModel.ExamModel;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("examDAO")
public class ExamDAOImpl implements IExamDAO {

    @Override
    public ResultInfo createExam(ExamModel model) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            ExamMapper mapper = session.getMapper(ExamMapper.class);
            mapper.createExam(model);
            return new ResultInfo(true, "成功创建考试", model.getExamId());
        }
        catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(ExamDAOImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    public ResultInfo deleteExam(int examId) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            ExamMapper mapper = session.getMapper(ExamMapper.class);
            mapper.deleteExam(examId);
            return new ResultInfo(true, "成功取消考试", null);
        }
        catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(ExamDAOImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }
}
