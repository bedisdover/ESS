package cn.edu.nju.dao.userDAO;

import cn.edu.nju.config.Role;
import cn.edu.nju.mapper.userMapper.UserMapper;
import cn.edu.nju.model.userModel.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * implementation of user dao
 */
@Transactional
@Service(value = "userDAO")
public class UserDAOImpl implements IUserDAO {

    private static final int TEACHER_ROLE = 1;

    private final UserMapper userMapper;

    @Autowired
    public UserDAOImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public int getUserIdByEmail(String email) {
        return userMapper.getUserIdByEmail(email);
    }

    @Override
    public Role getRoleById(int userId) {
        int role = userMapper.getRoleById(userId);
        return role == TEACHER_ROLE ? Role.teacher : Role.student;
    }

    @Override
    public UserModel getUserInfoByEmail(String email) {
        return userMapper.getUserInfoByEmail(email);
    }

    @Override
    public UserModel getUserInfoById(int userId) {
        return userMapper.getUserInfoById(userId);
    }
}
