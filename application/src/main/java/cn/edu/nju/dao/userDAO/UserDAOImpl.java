package cn.edu.nju.dao.userDAO;

import cn.edu.nju.config.Role;
import cn.edu.nju.dao.DataException;
import cn.edu.nju.mapper.userMapper.UserMapper;
import cn.edu.nju.model.userModel.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public UserDAOImpl(@Qualifier("userMapper") UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public int getUserIdByEmail(String email) throws DataException {
        try {
            return userMapper.getUserIdByEmail(email);
        } catch (Exception e) {
            throw new DataException("该邮箱不存在");
        }
    }

    @Override
    public Role getRoleById(int userId) throws DataException {
        int role;
        try {
            role = userMapper.getRoleById(userId);
        } catch (Exception e) {
            throw new DataException("该用户不存在");
        }
        return role == TEACHER_ROLE ? Role.teacher : Role.student;
    }

    @Override
    public UserModel getUserInfoByEmail(
            String email) throws DataException {
        try {
            return userMapper.getUserInfoByEmail(email);
        } catch (Exception e) {
            throw new DataException("该邮箱不存在");
        }
    }

    @Override
    public UserModel getUserInfoById(
            int userId) throws DataException {
        try {
            return userMapper.getUserInfoById(userId);
        } catch (Exception e) {
            throw new DataException("该用户不存在");
        }
    }
}
