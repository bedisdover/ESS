package cn.edu.nju.mapper.courseMapper;

import cn.edu.nju.model.courseModel.CourseModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("courseMapper")
public interface CourseMapper {

    void addCourse(@Param("course") CourseModel course);

    void updateCourse(@Param("course") CourseModel course);

    int getRemovedCourseNum(@Param("courseId") int courseId);

    String getCourseKeyById(@Param("courseId") int courseId);

    List<CourseModel> getNotSelectCourses(@Param("userId") int userId);

    List<CourseModel> getSelectCourses(@Param("userId") int userId);
}
