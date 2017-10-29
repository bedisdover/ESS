package cn.edu.nju.info.examInfo;

import cn.edu.nju.model.examModel.QuestionModel;
import cn.edu.nju.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class QuestionInfo {

    private int questionId;
    private int courseId;
    private String content;
    private int level;
    private String answer;      // separated by comma
    private List<OptionInfo> options;

    public QuestionInfo() {
    }

    public QuestionInfo(int questionId, int courseId, String content,
                        int level, String answer, List<OptionInfo> options) {
        this.questionId = questionId;
        this.courseId = courseId;
        this.content = content;
        this.level = level;
        this.answer = answer;
        this.options = options;
    }

    public static List<QuestionModel> toModelList(List<QuestionInfo> list, String md5) {
        List<QuestionModel> result = new ArrayList<>(list.size());
        for (QuestionInfo info : list) {
            result.add(new QuestionModel(
                    info.getQuestionId(), info.getCourseId(),
                    info.getContent(), 1, md5, info.getLevel(),
                    info.getAnswer(), JsonUtil.toJson(info.getOptions())
            ));
        }
        return result;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<OptionInfo> getOptions() {
        return options;
    }

    public void setOptions(List<OptionInfo> options) {
        this.options = options;
    }
}
