package cn.edu.nju.service.courseService;

import cn.edu.nju.dao.courseDAO.ICourseDAO;
import cn.edu.nju.dao.userDAO.IUserDAO;
import cn.edu.nju.enumeration.Role;
import cn.edu.nju.vo.ResultInfo;
import cn.edu.nju.vo.courseVO.CourseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "courseService")
public class CourseServiceImpl implements ICourseService {

    private final ICourseDAO courseDAO;

    private final IUserDAO userDAO;

    @Autowired
    public CourseServiceImpl(ICourseDAO courseDAO, IUserDAO userDAO) {
        this.courseDAO = courseDAO;
        this.userDAO = userDAO;
    }

    @Override
    public ResultInfo addCourse(int userId, CourseInfo info) {
        if (userDAO.getRoleById(userId) != Role.teacher) {
            return new ResultInfo(false, "只有教师才能添加课程", null);
        }
        return courseDAO.addCourse(userId, info);
    }

    @Override
    public ResultInfo modifyCourse(int userId, CourseInfo info) {
        boolean doTeachTheCourse = courseDAO.getCourseUserRecordNum(info.getId(), userId) > 0;
        if (!doTeachTheCourse) {
            return new ResultInfo(false, "只有授课老师才能需改课程信息", null);
        }

        return courseDAO.updateCourse(info);
    }

    @Override
    public ResultInfo enrollCourse(int userId, int courseId, String courseKey) {
        boolean isTeacher = userDAO.getRoleById(userId) == Role.teacher;
        if (isTeacher) {
            return new ResultInfo(false, "教师无法选课", null);
        }

        boolean doSelectTheCourse = courseDAO.getCourseUserRecordNum(courseId, userId) > 0;
        if (doSelectTheCourse) {
            return new ResultInfo(false, "已经选择了这门课,无需重新加入", null);
        }

        String key = courseDAO.getCourseKeyById(courseId);
        if (!key.equals(courseKey)) {
            return new ResultInfo(false, "选课密码错误", null);
        }

        return courseDAO.enrollCourse(userId, courseId);
    }

    @Override
    public ResultInfo quitCourse(int userId, int courseId) {
        boolean isTeacher = userDAO.getRoleById(userId) == Role.teacher;
        if (isTeacher) {
            return new ResultInfo(false, "教师无法退课", null);
        }

        boolean doSelectTheCourse = courseDAO.getCourseUserRecordNum(courseId, userId) > 0;
        if (!doSelectTheCourse) {
            return new ResultInfo(false, "没有选择这门课,无需退出", null);
        }
        return courseDAO.quitCourse(userId, courseId);
    }

    @Override
    public ResultInfo getNotSelectCourses(int userId) {
        return courseDAO.getNotSelectCourses(userId);
    }

    @Override
    public ResultInfo getSelectCourses(int userId) {
        return courseDAO.getSelectCourses(userId);
    }
}
