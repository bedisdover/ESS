package cn.edu.nju.dao.accountDAO;

import cn.edu.nju.dao.SessionFactory;
import cn.edu.nju.mapper.accountMapper.AccountMapper;
import cn.edu.nju.vo.ResultInfo;
import cn.edu.nju.vo.accountVO.LoginInfo;
import cn.edu.nju.vo.accountVO.SigUpInfo;
import cn.edu.nju.po.userPO.UserModel;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * implementation of account dao interface
 */
@Service(value = "accountDAO")
public class AccountDAOImpl implements IAccountDAO {

    @Override
    public boolean isAccountValid(LoginInfo model) {
        int userNum;
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            userNum = mapper.getVerifiedAccountNumByEmailAndPassword(
                    model.getEmail(), model.getPassword()
            );
        }
        return userNum == 1;
    }

    @Override
    public boolean isAccountExist(String email) {
        int userNum;
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            userNum = mapper.getAccountNumByEmail(email);
        }
        return userNum == 1;
    }

    @Override
    public ResultInfo addUser(SigUpInfo model) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            mapper.addUser(new UserModel(
                    model.getName(), model.getEmail(), model.getPassword(),
                    model.getRole(), 1, 1
            ));
            return new ResultInfo(true, "已成功注册账号", null);
        }
        catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(AccountDAOImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }
}
