package cn.edu.nju.po.userPO;

import cn.edu.nju.vo.userVO.UserInfo;

public class UserModel {

    private int id;
    private String name;
    private String email;
    private String password;
    private int role;
    private int verified;
    private int enabled;

    public UserModel() {
    }

    public UserModel(int id, String name, String email, String password,
                     int role, int verified, int enabled) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.verified = verified;
        this.enabled = enabled;
    }

    public UserModel(String name, String email, String password,
                     int role, int verified, int enabled) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.verified = verified;
        this.enabled = enabled;
    }

    public UserInfo toUserInfo() {
        return new UserInfo(name, email, role);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
}
