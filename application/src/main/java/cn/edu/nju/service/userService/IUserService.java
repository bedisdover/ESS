package cn.edu.nju.service.userService;

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
}
