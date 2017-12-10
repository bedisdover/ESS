package cn.edu.nju.mapper.examMapper;

import cn.edu.nju.model.examModel.ExamModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "examMapper")
public interface ExamMapper {

    void createExam(@Param("exam")ExamModel exam) throws Exception;

    void deleteExam(@Param("examId")int examId) throws Exception;

    void updateExam(@Param("exam")ExamModel exam) throws Exception;

    void updateNumOfQuestions(
            @Param("examId") int examId,
            @Param("num") String num) throws Exception;

    int getCourseIdByExamId(
            @Param("examId") int examId) throws Exception;

    List<ExamModel> getExamList(
            @Param("courseId") int courseId) throws Exception;

    List<ExamModel> getJoinExam(
            @Param("email") String email) throws Exception;

    List<ExamModel> getCreateExam(
            @Param("courseIdList") List<Integer> courseIdList) throws Exception;

    ExamModel getExamModelById(
            @Param("examId") int examId) throws Exception;
}
