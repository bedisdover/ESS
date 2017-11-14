package cn.edu.nju.dao.examDAO;

import cn.edu.nju.model.examModel.StudentModel;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public interface IStudentDAO {

    List<StudentModel> getExamStudents(int courseId);

    boolean isStudentFileMD5Exist(String md5Value);

    void updateStudents(List<StudentModel> students) throws Exception;
}
