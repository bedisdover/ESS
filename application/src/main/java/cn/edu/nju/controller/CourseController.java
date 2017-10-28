package cn.edu.nju.controller;

import cn.edu.nju.service.courseService.ICourseService;
import cn.edu.nju.vo.ResultInfo;
import cn.edu.nju.vo.courseVO.CourseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * course service controller
 */
@Controller
@RequestMapping(value = "/course")
public class CourseController {

    private final ICourseService courseService;

    @Autowired
    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * add a course
     * @param session http session
     * @param info course information
     * @return success or not
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addCourse(HttpSession session, @ModelAttribute CourseInfo info) {
        Integer userId = AccountController.getUserId(session);
        return courseService.addCourse(userId, info);
    }

    /**
     * modify information of a course
     * @param session http session
     * @param info course information
     * @return success or not
     */
    @RequestMapping(value = "/modify")
    @ResponseBody
    public ResultInfo modifyCourse(HttpSession session, @ModelAttribute CourseInfo info) {
        Integer userId = AccountController.getUserId(session);
        return courseService.modifyCourse(userId, info);
    }

    /**
     * a student join in a course
     * @param session http session
     * @param courseId course courseId
     * @param courseKey course key
     * @return success or not
     */
    @RequestMapping(value = "/enroll", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo enrollCourse(HttpSession session, @RequestParam int courseId,
                                   @RequestParam String courseKey) {
        Integer userId = AccountController.getUserId(session);
        return courseService.enrollCourse(userId, courseId, courseKey);
    }

    /**
     * a student quit a course
     * @param session http session
     * @param courseId course id
     * @return success or not
     */
    @RequestMapping(value = "/drop", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo quitCourse(HttpSession session, @RequestParam int courseId) {
        Integer userId = AccountController.getUserId(session);
        return courseService.quitCourse(userId, courseId);
    }

    /**
     * get course list, not include selected courses
     * @param session http session
     * @return result
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ResultInfo getCourseList(HttpSession session) {
        Integer userId = AccountController.getUserId(session);
        return courseService.getNotSelectCourses(userId);
    }

    /**
     * get my course list
     * @param session http session
     * @return course list
     */
    @RequestMapping(value = "/my")
    @ResponseBody
    public ResultInfo getMyCourses(HttpSession session) {
        Integer userId = AccountController.getUserId(session);
        return courseService.getSelectCourses(userId);
    }
}
