package cn.edu.nju.service.userService;

import cn.edu.nju.dao.userDAO.IUserDAO;
import cn.edu.nju.vo.userVO.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * implementation of IUserService
 */
@Service(value = "userService")
public class UserServiceImpl implements IUserService {

    private final IUserDAO userDAO;

    @Autowired
    public UserServiceImpl(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public int getUserIdByEmail(String email) {
        return userDAO.getUserIdByEmail(email);
    }

    @Override
    public UserInfo getUserInfoByEmail(String email) {
        return userDAO.getUserInfoByEmail(email).toUserInfo();
    }

    @Override
    public UserInfo getUserInfoById(int userId) {
        return userDAO.getUserInfoById(userId).toUserInfo();
    }
}
