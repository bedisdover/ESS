package cn.edu.nju.service.accountService;

import cn.edu.nju.model.ResultModel;
import cn.edu.nju.model.accountModel.LoginModel;
import cn.edu.nju.model.accountModel.SigUpModel;

/**
 * interface of account service
 * handle business logic of login, logout and sign up
 */
public interface IAccountService {

    /**
     * tell if it is a valid account
     * @param model login information
     * @return valid or not
     */
    boolean isValidAccount(LoginModel model);

    /**
     * logout
     * @return logout result
     */
    ResultModel logout();

    /**
     * add a new user
     * @param model user information
     * @return sign up result
     */
    ResultModel signUp(SigUpModel model);
}
