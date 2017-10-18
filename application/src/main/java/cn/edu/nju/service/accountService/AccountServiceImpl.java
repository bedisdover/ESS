package cn.edu.nju.service.accountService;

import cn.edu.nju.dao.accountDAO.IAccountDAO;
import cn.edu.nju.model.ResultModel;
import cn.edu.nju.model.accountModel.LoginModel;
import cn.edu.nju.model.accountModel.SigUpModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * implementation of account service interface
 */
@Service(value = "accountService")
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountDAO accountDAO;

    @Override
    public boolean isValidAccount(LoginModel model) {
        return false;
    }

    @Override
    public ResultModel logout() {
        return null;
    }

    @Override
    public ResultModel signUp(SigUpModel model) {
        return null;
    }
}
