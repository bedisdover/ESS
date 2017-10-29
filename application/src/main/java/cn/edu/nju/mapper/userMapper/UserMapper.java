package cn.edu.nju.mapper.userMapper;

import cn.edu.nju.model.userModel.UserModel;

/**
 * user sql mapper
 */
public interface UserMapper {

    int getUserIdByEmail(String email);

    int getRoleById(int userId);

    UserModel getUserInfoByEmail(String email);

    UserModel getUserInfoById(int userId);
}
