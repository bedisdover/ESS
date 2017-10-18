package cn.edu.nju.controller;

import cn.edu.nju.model.ResultModel;
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

    @Autowired
    private IAccountService accountService;

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
        return null;
    }

    /**
     * logout
     * @param session http session
     * @return logout result
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel logout(HttpSession session) {
        return null;
    }

    /**
     * sign up
     * @param session http session
     * @param name user name
     * @param email user email
     * @param password user password
     * @param role teacher or student
     * @return sign up result
     */
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel signUp(HttpSession session, String name,
                              String email, String password, int role) {
        return null;
    }
}
