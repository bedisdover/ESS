package cn.edu.nju.service.userService;

import cn.edu.nju.dao.DataException;
import cn.edu.nju.dao.userDAO.IUserDAO;
import cn.edu.nju.info.ResultInfo;
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
    public ResultInfo getUserIdByEmail(String email) {
        try {
            return new ResultInfo(true, null,
                    userDAO.getUserIdByEmail(email));
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }
    }

    @Override
    public ResultInfo getUserInfoByEmail(String email) {
        try {
            return new ResultInfo(true, null,
                    userDAO.getUserInfoByEmail(email).toUserInfo());
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }
    }

    @Override
    public ResultInfo getUserInfoById(int userId) {
        try {
            return new ResultInfo(true, null,
                    userDAO.getUserInfoById(userId).toUserInfo());
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }
    }

    @Override
    public ResultInfo getUserRoleById(int userId) {
        try {
            return new ResultInfo(true, null,
                    userDAO.getRoleById(userId));
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }
    }
}
