package cn.edu.nju.service.accountService;

import cn.edu.nju.vo.ResultInfo;
import cn.edu.nju.model.accountModel.LoginModel;
import cn.edu.nju.model.accountModel.SigUpModel;

/**
 * interface of account service
 * handle business logic of login, logout and sign up
 */
public interface IAccountService {

    /**
     * tell if it is a valid account
     * @param model user information
     * @return valid or not
     */
    boolean isAccountValid(LoginModel model);

    /**
     * logout
     */
    void logout();

    /**
     * add a new user
     * @param model user information
     * @return sign up result
     */
    ResultInfo signUp(SigUpModel model);
}
