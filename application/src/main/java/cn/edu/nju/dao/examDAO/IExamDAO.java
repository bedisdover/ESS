package cn.edu.nju.dao.examDAO;

import cn.edu.nju.model.examModel.ExamModel;
import cn.edu.nju.model.examModel.ExamScoreModel;

import java.util.List;

public interface IExamDAO {

    // return exam id
    int createExam(ExamModel model) throws Exception;

    void deleteExam(int examId) throws Exception;

    void updateExam(ExamModel model) throws Exception;

    void updateNumOfQuestions(int examId, String num) throws Exception;

    int getCourseIdByExamId(int examId);

    String getPasswordByExamId(int examId);

    List<ExamModel> getExamList(int courseId);

    List<ExamModel> getJoinExam(String email);

    List<ExamModel> getCreateExam(List<Integer> courseIdList);

    ExamModel getExamModelById(int examId);

}
