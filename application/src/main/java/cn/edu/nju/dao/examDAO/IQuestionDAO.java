package cn.edu.nju.dao.examDAO;

import cn.edu.nju.dao.DataException;
import cn.edu.nju.model.examModel.QuestionModel;

import java.util.List;

public interface IQuestionDAO {

    boolean isMD5Exist(String md5);

    void saveQuestions(
            List<QuestionModel> questions) throws Exception;

    List<QuestionModel> getCourseQuestions(
            int courseId, int offset, int size) throws DataException;

    void deleteQuestions(
            int courseId, List<Integer> questionIdList) throws Exception;

    int getCourseIdByQuestionId(
            int questionId) throws DataException;

    int getLevelNumByCourseId(
            int courseId) throws DataException;

    int getNumOfQuestions(int courseId, int level);

    int getNumOfCourseQuestions(int courseId);

    double getTotalMarkOfQuestions(
            int examId,
            List<Integer> questionIds) throws DataException;

    List<QuestionModel> getAllQuestionsByCourseId(
            int courseId) throws DataException;

    QuestionModel getQuestionById(
            int questionId) throws DataException;
}
