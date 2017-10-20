package cn.edu.nju.service.accountService;

import cn.edu.nju.dao.accountDAO.IAccountDAO;
import cn.edu.nju.vo.ResultInfo;
import cn.edu.nju.vo.accountVO.LoginInfo;
import cn.edu.nju.vo.accountVO.SigUpInfo;
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
    public boolean isAccountValid(LoginInfo info) {
        return accountDAO.isAccountValid(info);
    }

    @Override
    public void logout() {
    }

    @Override
    public ResultInfo signUp(SigUpInfo info) {
        if (accountDAO.isAccountExist(new LoginInfo(
                info.getEmail(), info.getPassword(), info.getRole()))) {
            return new ResultInfo(false, "该账户已经存在", null);
        }

        ResultInfo result = accountDAO.addUser(info);
        if (result.isSuccess()) {
            return new ResultInfo(true, "成功注册账户", null);
        }
        else {
            return result;
        }
    }
}
