package cn.edu.nju.service.userService;

import cn.edu.nju.config.Role;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.userInfo.UserInfo;

/**
 * interface of user service
 */
public interface IUserService {

    /**
     * get user id by email
     * @param email user email
     * @return user id
     */
    ResultInfo getUserIdByEmail(String email);

    /**
     * get user information by email
     * @param email user email
     * @return user information
     */
    ResultInfo getUserInfoByEmail(String email);

    ResultInfo getUserInfoById(int userId);

    ResultInfo getUserRoleById(int userId);
}
