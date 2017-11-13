package cn.edu.nju.info.examInfo;

import cn.edu.nju.model.examModel.ExamModel;
import cn.edu.nju.utils.StringUtil;

import java.util.List;

public class ExamInfo {

    private int examId;
    private int courseId;
    private String name;
    private String password;
    private String startTime;   //yyyy-MM-dd HH:mm:ss
    private String endTime;     //yyyy-MM-dd HH:mm:ss

    private List<Integer> num;
    private List<Integer> maxNum;
    private List<Double> marks;
    private List<StudentInfo> students;

    public ExamInfo() {
    }

    public ExamInfo(int examId, int courseId, String name, String password,
                    List<Integer> num, List<Integer> maxNum,
                    List<Double> marks, List<StudentInfo> students) {
        this.examId = examId;
        this.courseId = courseId;
        this.name = name;
        this.password = password;
        this.num = num;
        this.maxNum = maxNum;
        this.marks = marks;
        this.students = students;
    }

    public ExamModel toModel() {
        return new ExamModel(
                examId, courseId, 1, name, password,
                StringUtil.stringify(num, ","), startTime, endTime
        );
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

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Double> getMarks() {
        return marks;
    }

    public void setMarks(List<Double> marks) {
        this.marks = marks;
    }

    public List<StudentInfo> getStudents() {
        return students;
    }

    public void setStudents(List<StudentInfo> students) {
        this.students = students;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
