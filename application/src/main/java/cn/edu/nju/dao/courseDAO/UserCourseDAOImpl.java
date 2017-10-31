package cn.edu.nju.dao.courseDAO;

import cn.edu.nju.dao.SessionFactory;
import cn.edu.nju.mapper.courseMapper.CourseMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("userCourseDAO")
public class UserCourseDAOImpl implements IUserCourseDAO {

    @Override
    public boolean doesUserHaveCourse(int userId, int courseId) {
        try (SqlSession session = SessionFactory.getInstance().openSession()) {
            CourseMapper mapper = session.getMapper(CourseMapper.class);
            return mapper.getCourseUserRecordNum(userId, courseId) > 0;
        }
    }
}
