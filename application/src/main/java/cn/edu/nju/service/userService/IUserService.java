package cn.edu.nju.service.userService;

import cn.edu.nju.vo.userVO.UserInfo;

/**
 * interface of user service
 */
public interface IUserService {

    /**
     * get user id by email
     * @param email user email
     * @return user id
     */
    int getUserIdByEmail(String email);

    /**
     * get user information by email
     * @param email user email
     * @return user information
     */
    UserInfo getUserInfoByEmail(String email);

    UserInfo getUserInfoById(int userId);
}
