package cn.edu.nju.service.accountService;

import cn.edu.nju.vo.ResultInfo;
import cn.edu.nju.vo.accountVO.LoginInfo;
import cn.edu.nju.vo.accountVO.SigUpInfo;

/**
 * interface of account service
 * handle business logic of login, logout and sign up
 */
public interface IAccountService {

    /**
     * tell if it is a valid account
     * @param info user information
     * @return valid or not
     */
    boolean isAccountValid(LoginInfo info);

    /**
     * logout
     */
    void logout();

    /**
     * add a new user
     * @param info user information
     * @return sign up result
     */
    ResultInfo signUp(SigUpInfo info);
}
