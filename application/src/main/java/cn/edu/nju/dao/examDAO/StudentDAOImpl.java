package cn.edu.nju.dao.examDAO;

import cn.edu.nju.dao.DataException;
import cn.edu.nju.mapper.examMapper.StudentMapper;
import cn.edu.nju.model.examModel.StudentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Service(value = "studentDAO")
public class StudentDAOImpl implements IStudentDAO {

    private final StudentMapper studentMapper;

    @Autowired
    public StudentDAOImpl(@Qualifier("studentMapper") StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public List<StudentModel> getCourseStudents(
            int courseId) throws DataException {
        try {
            return studentMapper.getCourseStudents(courseId);
        } catch (Exception e) {
            throw new DataException("该课程不存在");
        }
    }

    @Override
    public void deleteCourseStudents(
            int courseId, List<String> emails) throws Exception {
        studentMapper.deleteCourseStudents(courseId, emails);
    }

    @Override
    public List<StudentModel> getExamStudents(
            int examId, int courseId) throws DataException {
        try {
            return studentMapper.getExamStudents(examId, courseId);
        } catch (Exception e) {
            throw new DataException("该考试不存在");
        }
    }

    @Override
    public boolean isStudentFileMD5Exist(String md5Value) {
        return studentMapper.getStudentMD5Count(md5Value) > 0;
    }

    @Override
    public void updateStudents(List<StudentModel> students) throws Exception {
        for (StudentModel student : students) {
            studentMapper.updateStudent(student);
        }
    }
}
