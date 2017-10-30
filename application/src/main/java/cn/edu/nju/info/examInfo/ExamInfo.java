package cn.edu.nju.info.examInfo;

import java.util.List;

public class ExamInfo {

    private int examId;
    private int courseId;
    private List<Integer> num;
    private List<Integer> maxNum;

    public ExamInfo() {
    }

    public ExamInfo(int examId, int courseId,
                    List<Integer> num, List<Integer> maxNum) {
        this.examId = examId;
        this.courseId = courseId;
        this.num = num;
        this.maxNum = maxNum;
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

    public List<Integer> getNum() {
        return num;
    }

    public void setNum(List<Integer> num) {
        this.num = num;
    }

    public List<Integer> getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(List<Integer> maxNum) {
        this.maxNum = maxNum;
    }
}