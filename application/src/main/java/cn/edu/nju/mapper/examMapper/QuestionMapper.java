package cn.edu.nju.mapper.examMapper;

import cn.edu.nju.model.examModel.QuestionModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "questionMapper")
public interface QuestionMapper {

    int getMD5Num(@Param("md5") String md5);

    void addQuestionList(
            @Param("questions") List<QuestionModel> questions) throws Exception;

    List<QuestionModel> getCourseQuestions(
            @Param("courseId") int courseId,
            @Param("offset") int offset,
            @Param("size") int size) throws Exception;

    void deleteQuestions(
            @Param("courseId") int courseId,
            @Param("questionIdList") List<Integer> questionIdList) throws Exception;

    int getCourseIdByQuestionId(
            @Param("questionId") int questionId) throws Exception;

    int getLevelNumByCourseId(
            @Param("courseId") int courseId) throws Exception;

    int getNumOfQuestions(
            @Param("courseId") int courseId,
            @Param("level") int level);

    int getNumOfCourseQuestions(
            @Param("courseId") int courseId);

    List<QuestionModel> getAllQuestionsByCourseId(
            @Param("courseId") int courseId) throws Exception;

    QuestionModel getQuestionById(
            @Param("questionId") int questionId) throws Exception;
}
