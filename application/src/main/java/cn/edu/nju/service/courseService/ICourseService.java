package cn.edu.nju.service.courseService;

import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.courseInfo.CourseInfo;

/**
 * interface of course service
 */
public interface ICourseService {

    /**
     * add a course
     * @param userId user id
     * @param info course information
     * @return success or not
     */
    ResultInfo addCourse(int userId, CourseInfo info);

    /**
     * modify information of a course
     * @param userId user id
     * @param info course information
     * @return success or not
     */
    ResultInfo modifyCourse(int userId, CourseInfo info);

    /**
     * make a student enroll a course
     * @param userId user id
     * @param courseId course id
     * @param courseKey course key
     * @return success or not
     */
    ResultInfo enrollCourse(int userId, int courseId, String courseKey);

    /**
     * make a student quit a course
     * @param userId user id
     * @param courseId course id
     * @return success or not
     */
    ResultInfo quitCourse(int userId, int courseId);

    /**
     * get course list
     * @param userId user id
     * @return course list result
     */
    ResultInfo getNotSelectCourses(int userId);

    /**
     * get user course list
     * @param userId user id
     * @return course list result
     */
    ResultInfo getSelectCourses(int userId);
}
