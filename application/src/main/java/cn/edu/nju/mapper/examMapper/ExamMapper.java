package cn.edu.nju.mapper.examMapper;

import cn.edu.nju.model.examModel.ExamModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service(value = "examMapper")
public interface ExamMapper {

    void createExam(@Param("exam")ExamModel exam);

    void deleteExam(@Param("examId")int examId);

    int getCourseIdByExamId(@Param("examId") int examId);

    void updateNumOfQuestions(@Param("examId") int exmaId,
                              @Param("num") String num);
}
