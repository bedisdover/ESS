package cn.edu.nju.mapper.examMapper;

import cn.edu.nju.model.examModel.QuestionModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "questionMapper")
public interface QuestionMapper {

    int getMD5Num(@Param("md5") String md5);

    void addQuestionList(@Param("questions") List<QuestionModel> questions);

    List<QuestionModel> getAllQuestions(
                        @Param("num") int num);

    void deleteQuestions(
                        @Param("courseId") int courseId,
                        @Param("questionIdList")
                        List<Integer> questionIdList);

    int getCourseIdByQuestionId(
                        @Param("questionId") int questionId);

    int getLevelNumByCourseId(
                        @Param("courseId") int courseId);

    int getNumOfQuestions(
                        @Param("courseId") int courseId,
                        @Param("level") int level);

    List<QuestionModel> getAllQuestionsByCourseId(
                        @Param("courseId") int courseId);

    QuestionModel getQuestionById(
                        @Param("questionId") int questionId);
}
