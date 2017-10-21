package cn.edu.nju.service.accountService;

import cn.edu.nju.dao.accountDAO.IAccountDAO;
import cn.edu.nju.utils.EmailUtil;
import cn.edu.nju.vo.ResultInfo;
import cn.edu.nju.vo.accountVO.LoginInfo;
import cn.edu.nju.vo.accountVO.SigUpInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * implementation of account service interface
 */
@Service(value = "accountService")
public class AccountServiceImpl implements IAccountService {

    private final IAccountDAO accountDAO;

    // map from uuid to sign up information
    private static ConcurrentHashMap<String, SigUpInfo> uuidMap =
            new ConcurrentHashMap<>();

    // map from email to uuid
    private static ConcurrentHashMap<String, String> emailMap =
            new ConcurrentHashMap<>();

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
        if (accountDAO.isAccountExist(info.getEmail())) {
            return new ResultInfo(false, "该账户已经存在", null);
        }

        String uuid = UUID.randomUUID().toString();
        ResultInfo result = EmailUtil.sendVerifyEmail(info.getEmail(), uuid);
        if (!result.isSuccess()) {
            return result;
        }

        if (emailMap.containsKey(info.getEmail())) {
            return new ResultInfo(
                    false, "已经发送了验证邮件,请先前往邮箱进行验证", null
            );
        }

        uuidMap.put(uuid, info);
        emailMap.put(info.getEmail(), uuid);
        return result;
    }

    @Override
    public ResultInfo verifyEmail(String key) {
        if (!uuidMap.containsKey(key)) {
            return new ResultInfo(false, "错误的验证链接", false);
        }

        SigUpInfo info = uuidMap.get(key);
        emailMap.remove(info.getEmail());
        uuidMap.remove(key);
        return accountDAO.addUser(info);
    }
}
