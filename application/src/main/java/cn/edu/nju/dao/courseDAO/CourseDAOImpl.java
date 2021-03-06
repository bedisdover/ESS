package cn.edu.nju.dao.courseDAO;

import cn.edu.nju.dao.DataException;
import cn.edu.nju.mapper.courseMapper.CourseMapper;
import cn.edu.nju.mapper.courseMapper.UserCourseMapper;
import cn.edu.nju.model.courseModel.CourseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(value = "courseDAO")
public class CourseDAOImpl implements ICourseDAO {

    private final CourseMapper courseMapper;

    private final UserCourseMapper userCourseMapper;

    @Autowired
    public CourseDAOImpl(@Qualifier("courseMapper") CourseMapper courseMapper,
                         @Qualifier("userCourseMapper") UserCourseMapper userCourseMapper) {
        this.courseMapper = courseMapper;
        this.userCourseMapper = userCourseMapper;
    }

    @Override
    public void addCourse(int userId, CourseModel model) throws Exception {
        boolean isRecordExist = courseMapper.getRemovedCourseNum(model.getId()) > 0;
        if (isRecordExist) {
            userCourseMapper.recoverRemovedRecord(userId, model.getId());
        }
        else {
            courseMapper.addCourse(model);
            int courseId = model.getId();
            userCourseMapper.addUserCourseRecord(userId, courseId);
        }
    }

    @Override
    public void updateCourse(CourseModel model) throws Exception {
        courseMapper.updateCourse(model);
    }

    @Override
    public void enrollCourse(int userId, int courseId) throws Exception {
        boolean isRecordExist = userCourseMapper.getRemovedRecordNum(userId, courseId) > 0;
        if (isRecordExist) {
            userCourseMapper.recoverRemovedRecord(userId, courseId);
        }
        else {
            userCourseMapper.addUserCourseRecord(userId, courseId);
        }
    }

    @Override
    public void quitCourse(int userId, int courseId) throws Exception {
        userCourseMapper.removeUserCourseRecord(userId, courseId);
    }

    @Override
    public String getCourseKeyById(int courseId) throws DataException {
        try {
            return courseMapper.getCourseKeyById(courseId);
        } catch (Exception e) {
            throw new DataException("该课程不存在");
        }
    }

    @Override
    public String getCourseNameById(int courseId) throws DataException {
        try {
            return courseMapper.getCourseNameById(courseId);
        } catch (Exception e) {
            throw new DataException("该课程不存在");
        }
    }

    @Override
    public List<CourseModel> getNotSelectCourses(int userId) throws DataException {
        try {
            return courseMapper.getNotSelectCourses(userId);
        } catch (Exception e) {
            throw new DataException("该用户不存在");
        }
    }

    @Override
    public List<CourseModel> getSelectCourses(int userId) throws DataException {
        try {
            return courseMapper.getSelectCourses(userId);
        } catch (Exception e) {
            throw new DataException("该用户不存在");
        }
    }
}
