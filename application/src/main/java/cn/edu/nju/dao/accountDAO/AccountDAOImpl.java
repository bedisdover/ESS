package cn.edu.nju.dao.accountDAO;

import cn.edu.nju.dao.SessionFactory;
import cn.edu.nju.mapper.accontMapper.AccountMapper;
import cn.edu.nju.model.ResultModel;
import cn.edu.nju.model.accountModel.LoginModel;
import cn.edu.nju.model.accountModel.SigUpModel;
import cn.edu.nju.model.accountModel.UserModel;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

/**
 * implementation of account dao interface
 */
@Service(value = "accountDAO")
public class AccountDAOImpl implements IAccountDAO {

    @Override
    public boolean isAccountValid(LoginModel model) {
        int userNum;
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            userNum = mapper.getAccountNumByEmailAndPassword(
                    model.getEmail(), model.getPassword()
            );
        }
        return userNum == 1;
    }

    @Override
    public boolean isAccountExist(LoginModel model) {
        int userNum;
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            userNum = mapper.getAccountNumByEmail(model.getEmail());
        }
        return userNum == 1;
    }

    @Override
    public ResultModel addUser(SigUpModel model) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            mapper.addUser(new UserModel(
                    model.getName(), model.getEmail(), model.getPassword(),
                    model.getRole(), 0, 1
            ));
        }
        return new ResultModel(true, "已成功注册账号");
    }
}
