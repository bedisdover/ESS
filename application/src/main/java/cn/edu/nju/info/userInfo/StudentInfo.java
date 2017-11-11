package cn.edu.nju.info.userInfo;

/**
 * Created by Jiayiwu on 17/11/11.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class StudentInfo {

    private String email;
    private String name;
    private int cls;        //1, 2, 3, 4

    public StudentInfo() {
    }

    public StudentInfo(String email, String name, int cls) {
        this.email = email;
        this.name = name;
        this.cls = cls;
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

    public int getCls() {
        return cls;
    }

    public void setCls(int cls) {
        this.cls = cls;
    }
}
