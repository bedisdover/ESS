package cn.edu.nju.dao.examDAO;

import cn.edu.nju.mapper.examMapper.StudentExamMapper;
import cn.edu.nju.mapper.examMapper.StudentMapper;
import cn.edu.nju.model.examModel.StudentModel;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final StudentExamMapper studentExamMapper;

    @Autowired
    public StudentDAOImpl(StudentMapper studentMapper,
                          StudentExamMapper studentExamMapper) {
        this.studentMapper = studentMapper;
        this.studentExamMapper = studentExamMapper;
    }

    @Override
    public List<StudentModel> getExamStudents(int courseId) {
        return studentMapper.getExamStudents(courseId);
    }

    @Override
    public boolean isStudentFileMD5Exist(String md5Value) {
        return studentMapper.getStudentMD5Count(md5Value) > 0;
    }

    @Override
    public void updateExamStudents(int examId, List<StudentModel> students) throws Exception {
        students.forEach((student) -> {
            studentMapper.updateStudent(student);
            studentExamMapper.createJoinExamRecordIfNotExist(examId, student.getEmail());
        });
    }
}
