package cn.edu.nju.model.accountModel;

/**
 * information required for login operation
 */
public class LoginModel {

    private String email;
    private String password;
    private int role;   // role of user: 0 for teacher, 1 for student

    public LoginModel() {
    }

    public LoginModel(String email, String password, int role) {
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
