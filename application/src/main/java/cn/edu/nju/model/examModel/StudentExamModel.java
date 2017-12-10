package cn.edu.nju.model.examModel;

public class StudentExamModel {

    private int examId;
    private String email;
    private String password;
    private int enable;

    public StudentExamModel() {
    }

    public StudentExamModel(int examId, String email,
                            String password, int enable) {
        this.examId = examId;
        this.email = email;
        this.password = password;
        this.enable = enable;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }
}
