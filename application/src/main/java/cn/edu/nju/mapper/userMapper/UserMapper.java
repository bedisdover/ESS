package cn.edu.nju.mapper.userMapper;

/**
 * user sql mapper
 */
public interface UserMapper {

    int getUserIdByEmail(String email);

    int getRoleById(int userId);
}
