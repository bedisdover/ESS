package cn.edu.nju.dao.examDAO;

import cn.edu.nju.dao.DataException;
import cn.edu.nju.model.examModel.StudentExamModel;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public interface IStudentExamDAO {

    void joinInExam(List<StudentExamModel> records) throws Exception;

    void quitExam(int examId, List<String> emails) throws Exception;

    List<String> getExamStudentEmails(
            int examId) throws DataException;

    String getExamPassword(
            int examId, String email) throws DataException;

    boolean doesStudentJoinExam(String password, String email, int examId);
}
