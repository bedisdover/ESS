package cn.edu.nju.dao.examDAO;

import cn.edu.nju.model.examModel.ExamModel;
import cn.edu.nju.model.examModel.StudentModel;

import java.util.List;

public interface IExamDAO {

    // return exam id
    int createExam(ExamModel model) throws Exception;

    void deleteExam(int examId) throws Exception;

    void updateExam(ExamModel model) throws Exception;

    void joinInExam(int examId, List<String> emails) throws Exception;

    void updateExamStudents(int examId, List<StudentModel> students) throws Exception;

    boolean isStudentFileMD5Exist(String md5Value);

    void updateNumOfQuestions(int examId, String num) throws Exception;

    int getCourseIdByExamId(int examId);

    List<StudentModel> getExamStudents(int courseId);

    List<ExamModel> getExamList(int courseId);

    ExamModel getExamModelById(int examId);

    void deletePaperById(int paperId) throws Exception;
}
