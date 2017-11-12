package cn.edu.nju.model.examModel;

/**
 * Created by Jiayiwu on 17/11/12.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class StudentModel {

    private String email;
    private String name;
    private String md5Value;
    private int studentId;
    private int cls;        //1, 2, 3, 4
    private int courseId;
    private int enable;

    public StudentModel() {
    }

    public StudentModel(String email, String name, String md5Value,
                        int studentId, int cls, int courseId, int enable) {
        this.email = email;
        this.name = name;
        this.md5Value = md5Value;
        this.studentId = studentId;
        this.cls = cls;
        this.courseId = courseId;
        this.enable = enable;
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

    public String getMd5Value() {
        return md5Value;
    }

    public void setMd5Value(String md5Value) {
        this.md5Value = md5Value;
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

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }
}
