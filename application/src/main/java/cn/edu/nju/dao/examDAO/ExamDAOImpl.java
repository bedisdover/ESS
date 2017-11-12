package cn.edu.nju.dao.examDAO;

import cn.edu.nju.mapper.examMapper.ExamMapper;
import cn.edu.nju.model.examModel.ExamModel;
import cn.edu.nju.model.examModel.StudentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("examDAO")
public class ExamDAOImpl implements IExamDAO {

    private final ExamMapper examMapper;

    @Autowired
    public ExamDAOImpl(ExamMapper examMapper) {
        this.examMapper = examMapper;
    }

    @Override
    public int createExam(ExamModel model) throws Exception {
        examMapper.createExam(model);
        return model.getExamId();
    }

    @Override
    public void deleteExam(int examId) throws Exception {
        examMapper.deleteExam(examId);
    }

    @Override
    public void joinInExam(int examId, List<String> emails) throws Exception {
        examMapper.createJoinExamRecords(examId, emails);
    }

    @Override
    public void updateExamStudents(int examId, List<StudentModel> students) throws Exception {
        students.forEach((student) -> {
            examMapper.updateStudent(student);
            examMapper.createJoinExamRecordIfNotExist(examId, student.getEmail());
        });
    }

    @Override
    public boolean isStudentFileMD5Exist(String md5Value) {
        return examMapper.getStudentMD5Count(md5Value) > 0;
    }

    @Override
    public void updateNumOfQuestions(int examId, String num) throws Exception {
        examMapper.updateNumOfQuestions(examId, num);
    }

    @Override
    public int getCourseIdByExamId(int examId) {
        return examMapper.getCourseIdByExamId(examId);
    }

    @Override
    public List<ExamModel> getExamList(int courseId) {
        return examMapper.getExamList(courseId);
    }

    @Override
    public ExamModel getExamModelById(int examId) {
        return examMapper.getExamModelById(examId);
    }

    @Override
    public void deletePaperById(int paperId) throws Exception {

    }
}
