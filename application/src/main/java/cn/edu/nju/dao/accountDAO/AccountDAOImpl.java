package cn.edu.nju.dao.accountDAO;

import cn.edu.nju.model.ResultModel;
import cn.edu.nju.model.accountModel.LoginModel;
import cn.edu.nju.model.accountModel.SigUpModel;
import org.springframework.stereotype.Service;

/**
 * implementation of account dao interface
 */
@Service(value = "accountDAO")
public class AccountDAOImpl implements IAccountDAO {

    @Override
    public boolean isAccountValid(LoginModel model) {
        return false;
    }

    @Override
    public ResultModel addUser(SigUpModel model) {
        return null;
    }
}
