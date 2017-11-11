package cn.edu.nju.mapper.accountMapper;

import cn.edu.nju.model.userModel.UserModel;
import org.springframework.stereotype.Service;

/**
 * account data sql mapper
 */
@Service("accountMapper")
public interface AccountMapper {

    int getAccountNumByEmail(String email);

    int getVerifiedAccountNumByEmailAndPassword(String email, String password);

    void addUser(UserModel model);
}
