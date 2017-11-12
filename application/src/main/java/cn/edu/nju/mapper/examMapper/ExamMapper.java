package cn.edu.nju.mapper.examMapper;

import cn.edu.nju.info.userInfo.StudentInfo;
import cn.edu.nju.model.examModel.ExamModel;
import cn.edu.nju.model.userModel.StudentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "examMapper")
public interface ExamMapper {

    void createExam(@Param("exam")ExamModel exam);

    void deleteExam(@Param("examId")int examId);

    void updateNumOfQuestions(@Param("examId") int examId,
                              @Param("num") String num);

    void createJoinExamRecords(@Param("examId") int examId,
                               @Param("emails") List<String> emails);

    void createJoinExamRecordIfNotExist(@Param("examId") int examId,
                                        @Param("email") String email);

    void updateStudent(@Param("students") StudentModel student);

    int getStudentMD5Count(@Param("md5Value") String md5Value);

    int getCourseIdByExamId(@Param("examId") int examId);

    List<ExamModel> getExamList(@Param("courseId") int courseId);

    ExamModel getExamModelById(@Param("examId") int examId);
}
