package cn.edu.nju.mapper.examMapper;

import cn.edu.nju.model.examModel.ExamModel;
import org.apache.ibatis.annotations.Param;

public interface ExamMapper {

    void createExam(@Param("exam")ExamModel exam);

    void deleteExam(@Param("examId")int examId);
}
