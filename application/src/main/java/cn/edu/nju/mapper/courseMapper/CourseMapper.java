package cn.edu.nju.mapper.courseMapper;

import cn.edu.nju.model.courseModel.CourseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("courseMapper")
public interface CourseMapper {

    void addCourse(CourseModel model);

    void addUserCourseRecord(int userId, int courseId);

    void updateCourse(CourseModel model);

    void removeUserCourseRecord(int userId, int courseId);

    int getCourseUserRecordNum(int userId, int courseId);

    int getRemovedRecordNum(int userId, int courseId);

    int getRemovedCourseNum(int courseId);

    void recoverRemovedRecord(int courseId);

    void recoverRemovedRecord(int userId, int courseId);

    String getCourseKeyById(int courseId);

    List<CourseModel> getNotSelectCourses(int userId);

    List<CourseModel> getSelectCourses(int userId);
}
