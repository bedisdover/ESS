package cn.edu.nju.service.accountService;

import cn.edu.nju.dao.accountDAO.IAccountDAO;
import cn.edu.nju.vo.ResultInfo;
import cn.edu.nju.model.accountModel.LoginModel;
import cn.edu.nju.model.accountModel.SigUpModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * implementation of account service interface
 */
@Service(value = "accountService")
public class AccountServiceImpl implements IAccountService {

    private final IAccountDAO accountDAO;

    @Autowired
    public AccountServiceImpl(IAccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public boolean isAccountValid(LoginModel model) {
        return accountDAO.isAccountValid(model);
    }

    @Override
    public void logout() {
    }

    @Override
    public ResultInfo signUp(SigUpModel model) {
        if (accountDAO.isAccountExist(new LoginModel(
                model.getEmail(), model.getPassword(), model.getRole()))) {
            return new ResultInfo(false, "该账户已经存在");
        }

        ResultInfo result = accountDAO.addUser(model);
        if (result.isSuccess()) {
            return new ResultInfo(true, "成功注册账户");
        }
        else {
            return result;
        }
    }
}
