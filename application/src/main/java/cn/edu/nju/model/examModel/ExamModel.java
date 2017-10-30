package cn.edu.nju.model.examModel;

public class ExamModel {

    int examId;
    int courseId;
    int enable;
    String num;

    public ExamModel() {
    }

    public ExamModel(int examId, int courseId, int enable, String num) {
        this.examId = examId;
        this.courseId = courseId;
        this.enable = enable;
        this.num = num;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
