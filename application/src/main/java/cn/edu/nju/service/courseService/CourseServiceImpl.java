package cn.edu.nju.service.courseService;

import cn.edu.nju.dao.courseDAO.ICourseDAO;
import cn.edu.nju.dao.userDAO.IUserDAO;
import cn.edu.nju.enumeration.Role;
import cn.edu.nju.vo.ResultInfo;
import cn.edu.nju.vo.courseVO.CourseListResult;
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
            return new ResultInfo(false, "只有教师才能添加课程");
        }
        return courseDAO.addCourse(userId, info);
    }

    @Override
    public ResultInfo modifyCourse(int userId, CourseInfo info) {
        boolean doTeachTheCourse = courseDAO.getCourseUserRecordNum(info.getId(), userId) > 0;
        if (!doTeachTheCourse) {
            return new ResultInfo(false, "只有授课老师才能需改课程信息");
        }

        return courseDAO.updateCourse(info);
    }

    @Override
    public ResultInfo enrollCourse(int userId, int courseId, String courseKey) {
        boolean isTeacher = userDAO.getRoleById(userId) == Role.teacher;
        if (isTeacher) {
            return new ResultInfo(false, "教师无法选课");
        }

        boolean doSelectTheCourse = courseDAO.getCourseUserRecordNum(courseId, userId) > 0;
        if (doSelectTheCourse) {
            return new ResultInfo(false, "已经选择了这门课,无需重新加入");
        }

        String key = courseDAO.getCourseKeyById(courseId);
        if (!key.equals(courseKey)) {
            return new ResultInfo(false, "选课密码错误");
        }

        return courseDAO.enrollCourse(userId, courseId);
    }

    @Override
    public ResultInfo quitCourse(int userId, int courseId) {
        boolean isTeacher = userDAO.getRoleById(userId) == Role.teacher;
        if (isTeacher) {
            return new ResultInfo(false, "教师无法退课");
        }

        boolean doSelectTheCourse = courseDAO.getCourseUserRecordNum(courseId, userId) > 0;
        if (!doSelectTheCourse) {
            return new ResultInfo(false, "没有选择这门课,无需退出");
        }
        return courseDAO.quitCourse(userId, courseId);
    }

    @Override
    public CourseListResult getCourseList(int userId, int page, int size) {
        if (page < 0 || size < 0 || (long) page * size > Integer.MAX_VALUE) {
            return new CourseListResult(
                    new ResultInfo(false, "非法参数"), null
            );
        }
        return courseDAO.getCourseList(userId, page * size);
    }

    @Override
    public CourseListResult getUserCourseList(int id) {
        return courseDAO.getCourseListById(id);
    }
}
