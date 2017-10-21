package cn.edu.nju.vo.courseVO;

public class CourseInfo {

    private String name;
    private int grade;
    private String cls;
    private int year;
    private int term;
    private String password;
    private int id;

    public CourseInfo() {
    }

    public CourseInfo(String name, int grade, String cls,
                      int year, int term, String password) {
        this.name = name;
        this.grade = grade;
        this.cls = cls;
        this.year = year;
        this.term = term;
        this.password = password;
    }

    public CourseInfo(String name, int grade, String cls,
                      int year, int term, String password, int id) {
        this.name = name;
        this.grade = grade;
        this.cls = cls;
        this.year = year;
        this.term = term;
        this.password = password;
        this.id = id;
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
}
