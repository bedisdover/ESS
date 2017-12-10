package cn.edu.nju.service.courseService;

import cn.edu.nju.dao.DataException;
import cn.edu.nju.dao.courseDAO.ICourseDAO;
import cn.edu.nju.dao.courseDAO.IUserCourseDAO;
import cn.edu.nju.dao.userDAO.IUserDAO;
import cn.edu.nju.config.Role;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.courseInfo.CourseInfo;
import cn.edu.nju.model.courseModel.CourseModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "courseService")
public class CourseServiceImpl implements ICourseService {

    private final ICourseDAO courseDAO;

    private final IUserDAO userDAO;

    private final IUserCourseDAO userCourseDAO;

    @Autowired
    public CourseServiceImpl(ICourseDAO courseDAO,
                             IUserDAO userDAO,
                             IUserCourseDAO userCourseDAO) {
        this.courseDAO = courseDAO;
        this.userDAO = userDAO;
        this.userCourseDAO = userCourseDAO;
    }

    @Override
    public ResultInfo addCourse(int userId, CourseInfo info) {
        Role role;
        try {
            role = userDAO.getRoleById(userId);
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }

        if (role != Role.teacher) {
            return new ResultInfo(false, "只有教师才能添加课程", null);
        }

        try {
            courseDAO.addCourse(userId, info.toModel());
            return new ResultInfo(true, "成功添加课程", null);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(CourseServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    public ResultInfo modifyCourse(int userId, CourseInfo info) {
        boolean doTeachTheCourse = userCourseDAO.getCourseUserRecordNum(info.getId(), userId) > 0;
        if (!doTeachTheCourse) {
            return new ResultInfo(false, "只有授课老师才能需改课程信息", null);
        }

        try {
            courseDAO.updateCourse(info.toModel());
            return new ResultInfo(true, "成功修改课程信息", null);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(CourseServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    public ResultInfo enrollCourse(int userId, int courseId, String courseKey) {
        Role role;
        try {
            role = userDAO.getRoleById(userId);
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }

        boolean isTeacher = role == Role.teacher;
        if (isTeacher) {
            return new ResultInfo(false, "教师无法选课", null);
        }

        boolean doSelectTheCourse = userCourseDAO.getCourseUserRecordNum(courseId, userId) > 0;
        if (doSelectTheCourse) {
            return new ResultInfo(false, "已经选择了这门课,无需重新加入", null);
        }

        String key;
        try {
            key = courseDAO.getCourseKeyById(courseId);
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }

        if (!key.equals(courseKey)) {
            return new ResultInfo(false, "选课密码错误", null);
        }

        try {
            courseDAO.enrollCourse(userId, courseId);
            return new ResultInfo(true, "成功选课", null);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(CourseServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    public ResultInfo quitCourse(int userId, int courseId) {
        Role role;
        try {
            role = userDAO.getRoleById(userId);
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }

        boolean isTeacher = role == Role.teacher;
        if (isTeacher) {
            return new ResultInfo(false, "教师无法退课", null);
        }

        boolean doSelectTheCourse = userCourseDAO.getCourseUserRecordNum(courseId, userId) > 0;
        if (!doSelectTheCourse) {
            return new ResultInfo(false, "没有选择这门课,无需退出", null);
        }

        try {
            courseDAO.quitCourse(userId, courseId);
            return new ResultInfo(true, "退课成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(CourseServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @Override
    public ResultInfo getNotSelectCourses(int userId) {
        List<CourseModel> list;
        try {
            list = courseDAO.getNotSelectCourses(userId);
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }

        return new ResultInfo(
                true, "成功获取课程信息列表",
                CourseModel.toInfoList(list)
        );
    }

    @Override
    public ResultInfo getSelectCourses(int userId) {
        List<CourseModel> list;
        try {
            list = courseDAO.getSelectCourses(userId);
        } catch (DataException e) {
            return new ResultInfo(false, e.getMessage(), null);
        }

        return new ResultInfo(
                true, "成功获取所选课程信息列表",
                CourseModel.toInfoList(list)
        );
    }
}
