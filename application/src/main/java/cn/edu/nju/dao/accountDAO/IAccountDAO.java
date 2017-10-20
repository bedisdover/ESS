package cn.edu.nju.dao.accountDAO;

import cn.edu.nju.vo.ResultInfo;
import cn.edu.nju.vo.accountVO.LoginInfo;
import cn.edu.nju.vo.accountVO.SigUpInfo;

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
     * @param model user information
     * @return exist or not
     */
    boolean isAccountExist(LoginInfo model);

    /**
     * add a user
     * @param model user information
     * @return success or not
     */
    ResultInfo addUser(SigUpInfo model);
}
