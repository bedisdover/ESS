package cn.edu.nju.model.examModel;

import cn.edu.nju.info.examInfo.ExamInfo;
import cn.edu.nju.info.userInfo.StudentInfo;

import java.util.ArrayList;
import java.util.List;

public class ExamModel {

    private int examId;
    private int courseId;
    private int enable;
    private String name;
    private String password;
    private String num;             // num of questions with each level, separated by ','
    private String startTime;       // yyyy-MM-dd HH:mm:ss
    private String endTime;         // yyyy-MM-dd HH:mm:ss
    private String students;        // list of email of student involved in the exam, separated by ','

    public ExamModel() {
    }

    public ExamModel(int examId, int courseId, int enable,
                     String name, String password, String num,
                     String startTime, String endTime, String students) {
        this.examId = examId;
        this.courseId = courseId;
        this.enable = enable;
        this.name = name;
        this.password = password;
        this.num = num;
        this.startTime = startTime;
        this.endTime = endTime;
        this.students = students;
    }

    public static List<ExamInfo> toInfoList(List<ExamModel> list,
                                            List<Integer> maxNum,
                                            List<Double> marks,
                                            List<StudentInfo> students) {
        List<ExamInfo> result = new ArrayList<>(list.size());
        for (ExamModel model : list) {
            String[] array = model.getNum().split(",");
            List<Integer> num = new ArrayList<>();
            for (String str : array) {
                num.add(Integer.parseInt(str));
            }
            result.add(new ExamInfo(
                    model.getExamId(), model.getCourseId(),
                    model.getName(), model.getPassword(), num,
                    maxNum, marks, students
            ));
        }
        return result;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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

    public String getStudents() {
        return students;
    }

    public void setStudents(String students) {
        this.students = students;
    }
}
