package cn.edu.nju.controller;

import cn.edu.nju.model.ResultModel;
import cn.edu.nju.model.accountModel.LoginModel;
import cn.edu.nju.model.accountModel.SigUpModel;
import cn.edu.nju.service.accountService.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * controller for handling account request
 * including login, logout and sign up
 */
@Controller
public class AccountController {

    private final IAccountService accountService;

    private static final String LOGIN_KEY = "user";

    @Autowired
    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * login
     * @param session http session
     * @param email user email
     * @param password user password
     * @param role teacher or student
     * @return login result
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel login(HttpSession session, String email,
                             String password, int role) {
        if (session.getAttribute(LOGIN_KEY) == null) {
            if (accountService.isAccountValid(new LoginModel(email, password, role))) {
                session.setAttribute(LOGIN_KEY, email);
                return new ResultModel(true, "登录成功");
            }
            else {
                return new ResultModel(false, "账号与密码不一致");
            }
        }
        else {
            return new ResultModel(false, "账号已经登录,无需重新登录");
        }
    }

    /**
     * logout
     * @param session http session
     * @return logout result
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel logout(HttpSession session) {
        accountService.logout();
        session.setAttribute(LOGIN_KEY, null);
        return new ResultModel(true, "成功退出");
    }

    /**
     * sign up
     * @param name user name
     * @param email user email
     * @param password user password
     * @param role teacher or student
     * @return sign up result
     */
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel signUp(String name,
                              String email, String password, int role) {
        return accountService.signUp(new SigUpModel(
                name, email, password, role
        ));
    }
}
