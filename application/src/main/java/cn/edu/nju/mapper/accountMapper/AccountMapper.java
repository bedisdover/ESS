package cn.edu.nju.mapper.accountMapper;

import cn.edu.nju.model.userModel.UserModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * account data sql mapper
 */
@Service("accountMapper")
public interface AccountMapper {

    int getAccountNumByEmail(@Param("email") String email);

    int getVerifiedAccountNumByEmailAndPassword(
            @Param("email") String email,
            @Param("password") String password);

    void addUser(@Param("user") UserModel user) throws Exception;
}
