package cn.edu.nju.dao.courseDAO;

import cn.edu.nju.dao.DataException;
import cn.edu.nju.mapper.courseMapper.UserCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("userCourseDAO")
public class UserCourseDAOImpl implements IUserCourseDAO {

    private final UserCourseMapper userCourseMapper;

    @Autowired
    public UserCourseDAOImpl(@Qualifier("userCourseMapper") UserCourseMapper userCourseMapper) {
        this.userCourseMapper = userCourseMapper;
    }

    @Override
    public boolean doesUserHaveCourse(int userId, int courseId) {
        return userCourseMapper.getCourseUserRecordNum(userId, courseId) > 0;
    }

    @Override
    public int getCourseUserRecordNum(int courseId, int userId) {
        return userCourseMapper.getCourseUserRecordNum(userId, courseId);
    }

    @Override
    public List<Integer> getCourseIdsByUserId(int userId) throws DataException {
        try {
            return userCourseMapper.getCourseIdsByUserId(userId);
        } catch (Exception e) {
            throw new DataException("该用户不存在");
        }
    }
}
