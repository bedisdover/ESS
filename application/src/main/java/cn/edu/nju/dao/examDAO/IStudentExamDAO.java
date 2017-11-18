package cn.edu.nju.dao.examDAO;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public interface IStudentExamDAO {

    void joinInExam(int examId, List<String> emails) throws Exception;

    void quitExam(int examId, List<String> emails) throws Exception;

    List<String> getExamStudentEmails(int examId);

    boolean doesStudentJoinExam(String email, int examId);
}
