package cn.edu.nju.dao.examDAO;

import cn.edu.nju.dao.DataException;
import cn.edu.nju.mapper.examMapper.StudentExamMapper;
import cn.edu.nju.model.examModel.StudentExamModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public StudentExamDAOImpl(@Qualifier("studentExamMapper") StudentExamMapper studentExamMapper) {
        this.studentExamMapper = studentExamMapper;
    }

    @Override
    public void joinInExam(List<StudentExamModel> records) throws Exception {
        studentExamMapper.createJoinExamRecords(records);
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
    public String getExamPassword(
            int examId, String email) throws DataException {
        try {
            return studentExamMapper.getExamPassword(examId, email);
        } catch (Exception e) {
            throw new DataException("该考试不存在");
        }
    }

    @Override
    public boolean doesStudentJoinExam(String password, String email, int examId) {
        return studentExamMapper.getStudentExamRecordNum(examId, email, password) > 0;
    }

}
