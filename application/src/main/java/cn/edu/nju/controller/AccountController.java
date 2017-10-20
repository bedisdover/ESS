package cn.edu.nju.controller;

import cn.edu.nju.vo.ResultInfo;
import cn.edu.nju.vo.accountVO.LoginInfo;
import cn.edu.nju.vo.accountVO.SigUpInfo;
import cn.edu.nju.service.accountService.IAccountService;
import cn.edu.nju.service.userService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * controller for handling account request
 * including login, logout and sign up
 */
@Controller
public class AccountController {

    private final IAccountService accountService;

    private final IUserService userService;

    private static final String LOGIN_KEY = "user";

    @Autowired
    public AccountController(IAccountService accountService, IUserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    /**
     * login
     * @param session http session
     * @param info information required for login
     * @return login result
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo login(HttpSession session, @RequestParam LoginInfo info) {
        if (session.getAttribute(LOGIN_KEY) == null) {
            if (accountService.isAccountValid(info)) {
                session.setAttribute(LOGIN_KEY,
                        userService.getUserIdByEmail(info.getEmail())
                );
                return new ResultInfo(true, "登录成功");
            }
            else {
                return new ResultInfo(false, "账号与密码不一致");
            }
        }
        else {
            return new ResultInfo(false, "账号已经登录,无需重新登录");
        }
    }

    /**
     * logout
     * @param session http session
     * @return logout result
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    public ResultInfo logout(HttpSession session) {
        if (getUserId(session) == null) {
            return new ResultInfo(false, "请先登录");
        }
        accountService.logout();
        session.setAttribute(LOGIN_KEY, null);
        return new ResultInfo(true, "成功退出");
    }

    /**
     * sign up
     * @param info information required for sign up
     * @return sign up result
     */
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo signUp(@RequestParam SigUpInfo info) {
        return accountService.signUp(info);
    }

    /**
     * get user id, if not login, return null
     * @param session http session
     * @return user id
     */
    static Integer getUserId(HttpSession session) {
        return (Integer) session.getAttribute(LOGIN_KEY);
    }
}
