package cn.edu.nju.service.examService;

import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.ExamInfo;

import java.io.InputStream;

public interface IExamService {

    ResultInfo createExam(int userId, ExamInfo examInfo) throws Exception;

    ResultInfo updateExam(int userId,ExamInfo examInfo) throws Exception;

    ResultInfo deleteExam(int userId, int examId) throws Exception;

    ResultInfo getExamList(int courseId);

    ResultInfo getAllExams(int userId);

    ResultInfo getExamStatistics(int userId, int examId);

    ResultInfo getAnsweredPaper(int userId, int examId);

    ResultInfo getAnsweredPaper(int userId, int examId, String email);

    String generateExamResultFile(int userId, int examId, String context);

}
