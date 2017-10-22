package cn.edu.nju.dao.userDAO;

import cn.edu.nju.enumeration.Role;
import cn.edu.nju.po.userPO.UserModel;

/**
 * interface of user dao
 */
public interface IUserDAO {

    /**
     * get user id by email
     * @param email user email
     * @return user id
     */
    int getUserIdByEmail(String email);

    Role getRoleById(int userId);

    UserModel getUserInfoByEmail(String email);

    UserModel getUserInfoById(int userId);
}
