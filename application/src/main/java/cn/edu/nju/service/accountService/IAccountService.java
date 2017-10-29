package cn.edu.nju.service.accountService;

import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.accountInfo.LoginInfo;
import cn.edu.nju.info.accountInfo.SigUpInfo;

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

    /**
     * verify email
     * @param key uuid
     * @return success or not
     */
    ResultInfo verifyEmail(String key);
}
