package cn.edu.nju.dao.examDAO;

import cn.edu.nju.model.examModel.StudentModel;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public interface IStudentDAO {

    List<StudentModel> getCourseStudents(int courseId);

    List<StudentModel> getExamStudents(int examId);

    boolean isStudentFileMD5Exist(String md5Value);

    void updateStudents(List<StudentModel> students) throws Exception;
}
