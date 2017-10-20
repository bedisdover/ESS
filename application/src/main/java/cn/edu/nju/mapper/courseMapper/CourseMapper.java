package cn.edu.nju.mapper.courseMapper;

import cn.edu.nju.po.coursePO.CourseModel;

import java.util.List;

public interface CourseMapper {

    void addCourse(CourseModel model);

    void addUserCourseRecord(int userId, int courseId);

    void updateCourse(CourseModel model);

    void removeUserCourseRecord(int userId, int courseId);

    int getCourseUserRecordNum(int userId, int courseId);

    int getRemovedRecordNum(int userId, int courseId);

    int getRemovedCourseNum(int courseId);

    void recoverRemovedCourse(int courseId);

    void recoverRemovedCourse(int userId, int courseId);

    String getCourseKeyById(int courseId);

    List<CourseModel> getCourseListBySize(int userId, int num);

    List<CourseModel> getCourseListById(int id);
}
