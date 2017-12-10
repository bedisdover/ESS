package cn.edu.nju.dao.examDAO;

import cn.edu.nju.dao.DataException;
import cn.edu.nju.model.examModel.ExamModel;

import java.util.List;

public interface IExamDAO {

    // return exam id
    int createExam(ExamModel model) throws Exception;

    void deleteExam(int examId) throws Exception;

    void updateExam(ExamModel model) throws Exception;

    void updateNumOfQuestions(int examId, String num) throws Exception;

    int getCourseIdByExamId(int examId) throws DataException;

    List<ExamModel> getExamList(int courseId) throws DataException;

    List<ExamModel> getJoinExam(String email) throws DataException;

    List<ExamModel> getCreateExam(List<Integer> courseIdList) throws DataException;

    ExamModel getExamModelById(int examId) throws DataException;

}
