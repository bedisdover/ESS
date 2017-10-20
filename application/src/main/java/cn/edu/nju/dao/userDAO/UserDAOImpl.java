package cn.edu.nju.dao.userDAO;

import cn.edu.nju.dao.SessionFactory;
import cn.edu.nju.enumeration.Role;
import cn.edu.nju.mapper.userMapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

/**
 * implementation of user dao
 */
@Service(value = "userDAO")
public class UserDAOImpl implements IUserDAO {

    private static final int TEACHER_ROLE = 1;

    @Override
    public int getUserIdByEmail(String email) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.getUserIdByEmail(email);
        }
    }

    @Override
    public Role getRoleById(int userId) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            int role = mapper.getRoleById(userId);
            return role == TEACHER_ROLE ? Role.teacher : Role.student;
        }
    }
}
