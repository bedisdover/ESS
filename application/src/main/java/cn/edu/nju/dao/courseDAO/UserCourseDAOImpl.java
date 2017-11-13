package cn.edu.nju.dao.courseDAO;

import cn.edu.nju.dao.SessionFactory;
import cn.edu.nju.mapper.courseMapper.CourseMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("userCourseDAO")
public class UserCourseDAOImpl implements IUserCourseDAO {

    private final CourseMapper courseMapper;

    @Autowired
    public UserCourseDAOImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public boolean doesUserHaveCourse(int userId, int courseId) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            CourseMapper mapper = session.getMapper(CourseMapper.class);
            return mapper.getCourseUserRecordNum(userId, courseId) > 0;
        }
    }

    @Override
    public int getCourseUserRecordNum(int courseId, int userId) {
        return courseMapper.getCourseUserRecordNum(userId, courseId);
    }

    @Override
    public List<Integer> getCourseIdsByUserId(int userId) {
        return null;
    }
}
