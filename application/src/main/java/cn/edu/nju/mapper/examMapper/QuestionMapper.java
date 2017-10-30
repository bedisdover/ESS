package cn.edu.nju.mapper.examMapper;

import cn.edu.nju.model.examModel.QuestionModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionMapper {

    int getMD5Num(String md5);

    void addQuestionList(List<QuestionModel> questions);

    List<QuestionModel> getAllQuestions(int num);

    void deleteQuestions(@Param("courseId") int courseId,
                         @Param("questionIdList") List<Integer> questionIdList);

    int getCourseIdByQuestionId(int questionId);

    void setMarkOfLevel(@Param("courseId") int courseId,
                        @Param("examId") int examId,
                        @Param("mark") double mark,
                        @Param("level") int level);
}
