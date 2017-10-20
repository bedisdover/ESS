package cn.edu.nju.vo.accountVO;

/**
 * information required for sign up
 */
public class SigUpInfo {

    private String name;
    private String email;
    private String password;
    private int role;   // role of user: 0 for teacher, 1 for student

    public SigUpInfo() {
    }

    public SigUpInfo(String name, String email, String password, int role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
