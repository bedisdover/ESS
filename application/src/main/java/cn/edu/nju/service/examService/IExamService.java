package cn.edu.nju.service.examService;

import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.ExamInfoForTeacher;

public interface IExamService {

    ResultInfo createExam(int userId, ExamInfoForTeacher examInfo) throws Exception;

    ResultInfo updateExam(int userId,ExamInfoForTeacher examInfo) throws Exception;

    ResultInfo deleteExam(int userId, int examId) throws Exception;

    ResultInfo getExamSimpleInfo(int examId);

    ResultInfo getExamList(int courseId);

    ResultInfo getAllExams(int userId);

    ResultInfo getExamStatistics(int userId, int examId);

    ResultInfo getAnsweredPaper(int userId, int examId);

    ResultInfo getAnsweredPaper(int userId, int examId, String email);

    String generateExamResultFile(int userId, int examId, String context);

}
