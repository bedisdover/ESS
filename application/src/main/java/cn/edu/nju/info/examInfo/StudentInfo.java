package cn.edu.nju.info.examInfo;

import cn.edu.nju.model.examModel.StudentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiayiwu on 17/11/11.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class StudentInfo {

    private String email;
    private String name;
    private int studentId;
    private int cls;        //1, 2, 3, 4
    private int courseId;

    public StudentInfo() {
    }

    public StudentInfo(String email, String name,
                       int studentId, int cls, int courseId) {
        this.email = email;
        this.name = name;
        this.studentId = studentId;
        this.cls = cls;
        this.courseId = courseId;
    }

    public static List<StudentModel> toModelList(List<StudentInfo> infoList,
                                                 String md5Value) {
        List<StudentModel> models = new ArrayList<>(infoList.size());
        infoList.forEach((info) -> models.add(info.toModel(md5Value)));
        return models;
    }

    public StudentModel toModel(String md5Value) {
        return new StudentModel(
                email, name, md5Value,
                studentId, cls, courseId, 1
        );
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCls() {
        return cls;
    }

    public void setCls(int cls) {
        this.cls = cls;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
