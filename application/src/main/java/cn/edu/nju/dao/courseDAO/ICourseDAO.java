package cn.edu.nju.dao.courseDAO;

import cn.edu.nju.model.courseModel.CourseModel;

import java.util.List;

public interface ICourseDAO {

    void addCourse(int userId, CourseModel model) throws Exception;

    void updateCourse(CourseModel model) throws Exception;

    void enrollCourse(int userId, int courseId) throws Exception;

    void quitCourse(int userId, int courseId) throws Exception;

    int getCourseUserRecordNum(int courseId, int userId);

    String getCourseKeyById(int courseId);

    List<CourseModel> getNotSelectCourses(int userId);

    List<CourseModel> getSelectCourses(int userId);
}
