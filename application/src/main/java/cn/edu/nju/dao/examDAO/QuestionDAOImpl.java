package cn.edu.nju.dao.examDAO;

import cn.edu.nju.dao.SessionFactory;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.mapper.examMapper.QuestionMapper;
import cn.edu.nju.model.examModel.QuestionModel;
import org.apache.ibatis.session.SqlSession;
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
    }
}
