package cn.edu.nju.po.coursePO;

import cn.edu.nju.vo.courseVO.CourseInfo;

public class CourseModel {

    private String name;
    private int grade;
    private String cls;
    private int year;
    private int term;
    private String password;
    private int id;
    private int enable = DEFAULT_ENABLE;

    private static final int DEFAULT_ENABLE = 1;

    public CourseModel() {
    }

    public CourseModel(CourseInfo info) {
        this.name = info.getName();
        this.grade = info.getGrade();
        this.cls = info.getCls();
        this.year = info.getYear();
        this.term = info.getTerm();
        this.password = info.getPassword();
        this.id = info.getId();
        this.enable = DEFAULT_ENABLE;
    }

    public CourseModel(String name, int grade, String cls, int year,
                       int term, String password, int id, int enable) {
        this.name = name;
        this.grade = grade;
        this.cls = cls;
        this.year = year;
        this.term = term;
        this.password = password;
        this.id = id;
        this.enable = enable;
    }

    public CourseInfo toCourseInfo() {
        return new CourseInfo(
                name, grade, cls, year, term, password, id
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }
}
