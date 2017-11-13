package cn.edu.nju.service.examService;

import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.LevelInfo;

import java.io.InputStream;
import java.util.List;

public interface IQuestionService {

    ResultInfo saveQuestion(int userId, int courseId, InputStream excelStream);

    ResultInfo getAllQuestions(Integer page, Integer size);

    ResultInfo deleteQuestions(int userId, List<Integer> questionIdList);

}
