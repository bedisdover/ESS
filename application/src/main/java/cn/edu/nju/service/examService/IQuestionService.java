package cn.edu.nju.service.examService;

import cn.edu.nju.info.ResultInfo;

import java.io.InputStream;
import java.util.List;

public interface IQuestionService {

    ResultInfo saveQuestion(int courseId, InputStream excelStream);

    ResultInfo getAllQuestions(int num);

    ResultInfo deleteQuestions(List<Integer> questionIdList);
}
