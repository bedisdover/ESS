package cn.edu.nju.mapper.examMapper;

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

    void createJoinExamRecords(@Param("examId") int examId,
                               @Param("emails") List<String> emails);

    void createJoinExamRecordIfNotExist(
                                @Param("examId") int examId,
                                @Param("email") String email);
}
