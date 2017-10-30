package cn.edu.nju.dao.courseDAO;

import cn.edu.nju.mapper.courseMapper.CourseMapper;
import cn.edu.nju.model.courseModel.CourseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "courseDAO")
public class CourseDAOImpl implements ICourseDAO {

    private final CourseMapper courseMapper;

    @Autowired
    public CourseDAOImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Transactional
    @Override
    public void addCourse(int userId, CourseModel model) throws Exception {
        boolean isRecordExist = courseMapper.getRemovedCourseNum(model.getId()) > 0;
        if (isRecordExist) {
            courseMapper.recoverRemovedRecord(model.getId());
        }
        else {
            courseMapper.addCourse(model);
            int courseId = model.getId();
            courseMapper.addUserCourseRecord(userId, courseId);
        }
    }

    @Transactional
    @Override
    public void updateCourse(CourseModel model) throws Exception {
        courseMapper.updateCourse(model);
    }

    @Transactional
    @Override
    public void enrollCourse(int userId, int courseId) throws Exception {
        boolean isRecordExist = courseMapper.getRemovedRecordNum(userId, courseId) > 0;
        if (isRecordExist) {
            courseMapper.recoverRemovedRecord(userId, courseId);
        }
        else {
            courseMapper.addUserCourseRecord(userId, courseId);
        }
    }

    @Transactional
    @Override
    public void quitCourse(int userId, int courseId) throws Exception {
        courseMapper.removeUserCourseRecord(userId, courseId);
    }

    @Override
    public int getCourseUserRecordNum(int courseId, int userId) {
        return courseMapper.getCourseUserRecordNum(userId, courseId);
    }

    @Override
    public String getCourseKeyById(int courseId) {
        return courseMapper.getCourseKeyById(courseId);
    }

    @Override
    public List<CourseModel> getNotSelectCourses(int userId) {
        return courseMapper.getNotSelectCourses(userId);
    }

    @Override
    public List<CourseModel> getSelectCourses(int userId) {
        return courseMapper.getSelectCourses(userId);
    }
}
