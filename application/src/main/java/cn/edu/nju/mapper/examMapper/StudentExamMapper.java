package cn.edu.nju.mapper.examMapper;

import cn.edu.nju.model.examModel.StudentExamModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Service(value = "studentExamMapper")
public interface StudentExamMapper {

    void createJoinExamRecords(
            @Param("records")List<StudentExamModel> records) throws Exception;

    void deleteJoinExamRecords(
            @Param("examId") int examId,
            @Param("emails") List<String> emails) throws Exception;

    List<String> getExamStudentEmails(
            @Param("examId") int examId);

    String getExamPassword(
            @Param("examId") int examId,
            @Param("email") String email) throws Exception;


    int getStudentExamRecordNum(
            @Param("examId") int examId,
            @Param("email") String email,
            @Param("password") String password);
}
