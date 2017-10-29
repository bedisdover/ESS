package cn.edu.nju.mapper.accountMapper;

import cn.edu.nju.model.userModel.UserModel;

/**
 * account data sql mapper
 */
public interface AccountMapper {

    int getAccountNumByEmail(String email);

    int getVerifiedAccountNumByEmailAndPassword(String email, String password);

    void addUser(UserModel model);
}
