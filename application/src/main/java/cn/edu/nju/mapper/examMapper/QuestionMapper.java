package cn.edu.nju.mapper.examMapper;

import cn.edu.nju.model.examModel.QuestionModel;

import java.util.List;

public interface QuestionMapper {

    int getMD5Num(String md5);

    void addQuestionList(List<QuestionModel> questions);
}
