package cn.edu.nju.controller;

import cn.edu.nju.config.AccountConfig;
import cn.edu.nju.utils.EncryptionUtil;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.accountInfo.LoginInfo;
import cn.edu.nju.info.accountInfo.SigUpInfo;
import cn.edu.nju.service.accountService.IAccountService;
import cn.edu.nju.service.userService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * controller for handling account request
 * including login, logout and sign up
 */
@Controller
public class AccountController {

    private final IAccountService accountService;

    private final IUserService userService;

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
    public ResultInfo login(HttpSession session,
                            @RequestBody LoginInfo info) {
        info.setPassword(EncryptionUtil.sha256(info.getPassword()));
        if (session.getAttribute(AccountConfig.LOGIN_KEY) == null) {
            if (accountService.isAccountValid(info)) {
                session.setAttribute(AccountConfig.LOGIN_KEY,
                        userService.getUserIdByEmail(info.getEmail())
                );
                return new ResultInfo(
                        true, "登录成功",
                        userService.getUserInfoByEmail(info.getEmail())
                );
            }
            else {
                return new ResultInfo(false, "账号与密码不一致", null);
            }
        }
        else {
            return new ResultInfo(
                    false, "账号已经登录,无需重新登录",
                    userService.getUserInfoByEmail(info.getEmail())
            );
        }
    }

    @RequestMapping(value = "/isLogin")
    @ResponseBody
    public ResultInfo isLogin(HttpSession session) {
        Integer userId = getUserId(session);
        if (userId == null) {
            return new ResultInfo(false, "没有登录", null);
        }
        return new ResultInfo(
                true, "已经登录",
                userService.getUserInfoById(userId)
        );
    }

    /**
     * logout
     * @param session http session
     * @return logout result
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    public ResultInfo logout(HttpSession session) {
        accountService.logout();
        session.setAttribute(AccountConfig.LOGIN_KEY, null);
        return new ResultInfo(true, "成功退出", null);
    }

    /**
     * sign up
     * @param info information required for sign up
     * @return sign up result
     */
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo signUp(@RequestBody SigUpInfo info) {
        info.setPassword(EncryptionUtil.sha256(info.getPassword()));
        return accountService.signUp(info);
    }

    @RequestMapping(value = "/mail/verify")
    @ResponseBody
    public ResultInfo verifyEmail(@RequestParam String key) {
        return accountService.verifyEmail(key);
    }

    /**
     * get user id, if not login, return null
     * @param session http session
     * @return user id
     */
    private Integer getUserId(HttpSession session) {
        return (Integer) session.getAttribute(AccountConfig.LOGIN_KEY);
    }
}
