package cn.edu.nju.service.examService;

import cn.edu.nju.info.ResultInfo;

public interface IExamService {

    ResultInfo createExam(int userId, int courseId, String num, String mark);

    ResultInfo updateExam(int userId, int examId, String num, String mark);
}
