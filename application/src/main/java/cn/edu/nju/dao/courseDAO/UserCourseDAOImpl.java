package cn.edu.nju.dao.courseDAO;

import cn.edu.nju.dao.SessionFactory;
import cn.edu.nju.mapper.courseMapper.CourseMapper;
import cn.edu.nju.mapper.courseMapper.UserCourseMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("userCourseDAO")
public class UserCourseDAOImpl implements IUserCourseDAO {

    private final UserCourseMapper userCourseMapper;

    @Autowired
    public UserCourseDAOImpl(UserCourseMapper userCourseMapper) {
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
    public List<Integer> getCourseIdsByUserId(int userId) {
        return userCourseMapper.getCourseIdsByUserId(userId);
    }
}
