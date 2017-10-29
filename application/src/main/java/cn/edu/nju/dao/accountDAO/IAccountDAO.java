package cn.edu.nju.dao.accountDAO;

import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.accountInfo.LoginInfo;
import cn.edu.nju.info.accountInfo.SigUpInfo;

/**
 * interface of account information access
 */
public interface IAccountDAO {

    /**
     * verify if it is a valid account
     * @param model user information
     * @return valid or not
     */
    boolean isAccountValid(LoginInfo model);

    /**
     * tell if account has existed
     * @param email user email
     * @return exist or not
     */
    boolean isAccountExist(String email);

    /**
     * add a user
     * @param model user information
     * @return success or not
     */
    ResultInfo addUser(SigUpInfo model);
}
