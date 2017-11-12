package cn.edu.nju.dao.userDAO;

import cn.edu.nju.config.Role;
import cn.edu.nju.model.userModel.UserModel;

/**
 * interface of user dao
 */
public interface IUserDAO {

    int getUserIdByEmail(String email);

    Role getRoleById(int userId);

    UserModel getUserInfoByEmail(String email);

    UserModel getUserInfoById(int userId);
}
