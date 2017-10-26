package cn.edu.nju.vo.accountVO;

/**
 * information required for login operation
 */
public class LoginInfo {

    private String email;
    private String password;
    private int role;   // role of user: 1 for teacher, 2 for student

    public LoginInfo() {
    }

    public LoginInfo(String email, String password, int role) {
        this.email = email;
        this.password = password;
        this.role = role;
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
