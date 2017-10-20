package cn.edu.nju.mapper.accountMapper;

import cn.edu.nju.po.userPO.UserModel;

/**
 * account data sql mapper
 */
public interface AccountMapper {

    int getAccountNumByEmail(String email);

    int getAccountNumByEmailAndPassword(String email, String password);

    void addUser(UserModel model);
}
