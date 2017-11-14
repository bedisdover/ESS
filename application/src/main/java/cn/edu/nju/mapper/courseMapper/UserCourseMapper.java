package cn.edu.nju.mapper.courseMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Service(value = "userCourseMapper")
public interface UserCourseMapper {

    void addUserCourseRecord(
                    @Param("userId") int userId,
                    @Param("courseId") int courseId);

    void removeUserCourseRecord(
                    @Param("userId") int userId,
                    @Param("courseId") int courseId);

    int getCourseUserRecordNum(
                    @Param("userId") int userId,
                    @Param("courseId") int courseId);

    int getRemovedRecordNum(
                    @Param("userId") int userId,
                    @Param("courseId") int courseId);

    void recoverRemovedRecord(
                    @Param("userId") int userId,
                    @Param("courseId") int courseId);

    List<Integer> getCourseIdsByUserId(
                    @Param("userId") int userId);
}
