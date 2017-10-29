package cn.edu.nju.service.examService;

import cn.edu.nju.info.ResultInfo;

import java.io.InputStream;

public interface IQuestionService {

    ResultInfo saveQuestion(int courseId, InputStream excelStream);
}
