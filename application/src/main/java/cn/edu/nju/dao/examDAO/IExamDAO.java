package cn.edu.nju.dao.examDAO;

import cn.edu.nju.model.examModel.ExamModel;
import cn.edu.nju.service.examService.ExamServiceImpl;

import java.util.List;

public interface IExamDAO {

    /**
     * insert an exam record
     * @param model exam information
     * @return exam id
     */
    int createExam(ExamModel model) throws Exception;

    void deleteExam(int examId) throws Exception;

    int getCourseIdByExamId(int examId);

    void updateNumOfQuestions(int examId, String num) throws Exception;

    List<ExamModel> getExamList(int courseId);
}
