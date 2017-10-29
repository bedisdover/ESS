package cn.edu.nju.model.examModel;

public class QuestionModel {

    private int questionId;
    private int courseId;
    private String content;
    private String md5Value;
    private int level;
    private String answer;
    private String optionJson;
    private int enable;

    public QuestionModel() {
    }

    public QuestionModel(int questionId, int courseId, String content, int enable,
                         String md5Value, int level, String answer, String optionJson) {
        this.questionId = questionId;
        this.courseId = courseId;
        this.content = content;
        this.md5Value = md5Value;
        this.level = level;
        this.answer = answer;
        this.optionJson = optionJson;
        this.enable = enable;
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

    public String getMd5Value() {
        return md5Value;
    }

    public void setMd5Value(String md5Value) {
        this.md5Value = md5Value;
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

    public String getOptionJson() {
        return optionJson;
    }

    public void setOptionJson(String optionJson) {
        this.optionJson = optionJson;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }
}
