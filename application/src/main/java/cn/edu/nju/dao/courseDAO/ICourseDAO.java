package cn.edu.nju.dao.courseDAO;

import cn.edu.nju.vo.ResultInfo;
import cn.edu.nju.vo.courseVO.CourseInfo;

public interface ICourseDAO {

    ResultInfo addCourse(int userId, CourseInfo model);

    ResultInfo updateCourse(CourseInfo model);

    ResultInfo enrollCourse(int userId, int courseId);

    ResultInfo quitCourse(int userId, int courseId);

    int getCourseUserRecordNum(int courseId, int userId);

    String getCourseKeyById(int courseId);

    ResultInfo getCourseList(int userId, int num);

    ResultInfo getCourseListById(int userId);
}
