package cn.edu.nju.mapper.examMapper;

import cn.edu.nju.model.examModel.ExamModel;
import cn.edu.nju.model.examModel.StudentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "examMapper")
public interface ExamMapper {

    void createExam(@Param("exam")ExamModel exam);

    void deleteExam(@Param("examId")int examId);

    void updateExam(@Param("exam")ExamModel exam);

    void updateNumOfQuestions(@Param("examId") int examId,
                              @Param("num") String num);

    int getCourseIdByExamId(@Param("examId") int examId);

    String getPasswordByExamId(@Param("examId") int examId);

    List<ExamModel> getExamList(@Param("courseId") int courseId);

    List<ExamModel> getJoinExam(@Param("email") String email);

    List<ExamModel> getCreateExam(@Param("courseIdList")
                                  List<Integer> courseIdList);

    ExamModel getExamModelById(@Param("examId") int examId);
}
