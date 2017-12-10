package cn.edu.nju.dao.accountDAO;

import cn.edu.nju.info.accountInfo.LoginInfo;
import cn.edu.nju.info.accountInfo.SigUpInfo;
import cn.edu.nju.mapper.accountMapper.AccountMapper;
import cn.edu.nju.model.userModel.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * implementation of account dao interface
 */
@Transactional
@Service(value = "accountDAO")
public class AccountDAOImpl implements IAccountDAO {

    private final AccountMapper accountMapper;

    @Autowired
    public AccountDAOImpl(@Qualifier("accountMapper") AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public boolean isAccountValid(LoginInfo model) {
        int userNum = accountMapper.getVerifiedAccountNumByEmailAndPassword(
                model.getEmail(), model.getPassword()
        );
        return userNum == 1;
    }

    @Override
    public boolean isAccountExist(String email) {
        int userNum = accountMapper.getAccountNumByEmail(email);
        return userNum == 1;
    }

    @Override
    public void addUser(SigUpInfo model) throws Exception {
        accountMapper.addUser(new UserModel(
                model.getName(), model.getEmail(), model.getPassword(),
                model.getRole(), 1, 1
        ));
    }
}
