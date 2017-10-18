package cn.edu.nju.dao.accountDAO;

import cn.edu.nju.model.ResultModel;
import cn.edu.nju.model.accountModel.LoginModel;
import cn.edu.nju.model.accountModel.SigUpModel;

/**
 * interface of account information access
 */
public interface IAccountDAO {

    /**
     * verify if it is a valid account
     * @param model user information
     * @return valid or not
     */
    boolean isAccountValid(LoginModel model);

    /**
     * tell if account has existed
     * @param model user information
     * @return exist or not
     */
    boolean isAccountExist(LoginModel model);

    /**
     * add a user
     * @param model user information
     * @return success or not
     */
    ResultModel addUser(SigUpModel model);
}
