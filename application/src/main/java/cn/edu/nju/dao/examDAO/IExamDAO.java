package cn.edu.nju.dao.examDAO;

import cn.edu.nju.info.userInfo.StudentInfo;
import cn.edu.nju.model.examModel.ExamModel;
import cn.edu.nju.model.userModel.StudentModel;

import java.util.List;

public interface IExamDAO {

    int createExam(ExamModel model) throws Exception;

    void deleteExam(int examId) throws Exception;

    void joinInExam(int examId, List<String> emails) throws Exception;

    void updateExamStudents(int examId, List<StudentModel> students) throws Exception;

    boolean isStudentFileMD5Exist(String md5Value);

    void updateNumOfQuestions(int examId, String num) throws Exception;

    int getCourseIdByExamId(int examId);

    List<ExamModel> getExamList(int courseId);

    ExamModel getExamModelById(int examId);

    void deletePaperById(int paperId) throws Exception;
}
