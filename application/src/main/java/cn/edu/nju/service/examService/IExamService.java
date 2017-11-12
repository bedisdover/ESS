package cn.edu.nju.service.examService;

import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.ExamInfo;

import java.io.InputStream;

public interface IExamService {

    ResultInfo createExam(int userId, ExamInfo examInfo, InputStream students) throws Exception;

    ResultInfo updateExam(int userId, int examId, String num, String mark);

    ResultInfo getExamList(int courseId);

    ResultInfo generatePaper(int examId);

    ResultInfo deletePaper(int paperId);
}
