package cn.edu.nju.dao.userDAO;

import cn.edu.nju.config.Role;
import cn.edu.nju.dao.DataException;
import cn.edu.nju.model.userModel.UserModel;

/**
 * interface of user dao
 */
public interface IUserDAO {

    int getUserIdByEmail(String email) throws DataException;

    Role getRoleById(int userId) throws DataException;

    UserModel getUserInfoByEmail(String email) throws DataException;

    UserModel getUserInfoById(int userId) throws DataException;
}
