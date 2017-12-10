package cn.edu.nju.mapper.userMapper;

import cn.edu.nju.model.userModel.UserModel;
import org.springframework.stereotype.Service;

/**
 * user sql mapper
 */
@Service(value = "userMapper")
public interface UserMapper {

    int getUserIdByEmail(String email) throws Exception;

    int getRoleById(int userId) throws Exception;

    UserModel getUserInfoByEmail(String email) throws Exception;

    UserModel getUserInfoById(int userId) throws Exception;
}
