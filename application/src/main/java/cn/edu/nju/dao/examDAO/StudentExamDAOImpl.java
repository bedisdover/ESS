package cn.edu.nju.dao.examDAO;

import cn.edu.nju.mapper.examMapper.StudentExamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Service(value = "studentExamDAO")
public class StudentExamDAOImpl implements IStudentExamDAO {

    private final StudentExamMapper studentExamMapper;

    @Autowired
    public StudentExamDAOImpl(StudentExamMapper studentExamMapper) {
        this.studentExamMapper = studentExamMapper;
    }

    @Override
    public void joinInExam(int examId, List<String> emails) throws Exception {
        studentExamMapper.createJoinExamRecords(examId, emails);
    }

    @Override
    public void quitExam(int examId, List<String> emails) throws Exception {
        studentExamMapper.deleteJoinExamRecords(examId, emails);
    }

    @Override
    public List<String> getExamStudentEmails(int examId) {
        return studentExamMapper.getExamStudentEmails(examId);
    }

    @Override
    public boolean doesStudentJoinExam(String email, int examId) {
        return studentExamMapper.getStudentExamRecordNum(examId, email) > 0;
    }

}
