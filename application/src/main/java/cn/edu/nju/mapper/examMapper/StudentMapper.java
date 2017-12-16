package cn.edu.nju.mapper.examMapper;

import cn.edu.nju.model.examModel.StudentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Service(value = "studentMapper")
public interface StudentMapper {

    List<StudentModel> getCourseStudents(
            @Param("courseId") int courseId) throws Exception;

    List<StudentModel> getExamStudents(
            @Param("examId") int examId,
            @Param("courseId") int courseId) throws Exception;

    void deleteCourseStudents(
            @Param("courseId") int courseId,
            @Param("emails") List<String> emails) throws Exception;

    void updateStudent(@Param("student") StudentModel student) throws Exception;

    int getStudentMD5Count(@Param("md5Value") String md5Value);
}
