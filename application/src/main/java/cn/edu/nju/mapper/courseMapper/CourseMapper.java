package cn.edu.nju.mapper.courseMapper;

import cn.edu.nju.model.courseModel.CourseModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("courseMapper")
public interface CourseMapper {

    void addCourse(@Param("course") CourseModel course) throws Exception;

    void updateCourse(@Param("course") CourseModel course) throws Exception;

    int getRemovedCourseNum(@Param("courseId") int courseId);

    String getCourseKeyById(@Param("courseId") int courseId) throws Exception;

    String getCourseNameById(@Param("courseId") int courseId) throws Exception;

    List<CourseModel> getNotSelectCourses(@Param("userId") int userId) throws Exception;

    List<CourseModel> getSelectCourses(@Param("userId") int userId) throws Exception;
}
