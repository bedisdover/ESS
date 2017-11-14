package cn.edu.nju.dao.courseDAO;

import java.util.List;

public interface IUserCourseDAO {

    boolean doesUserHaveCourse(int userId, int courseId);

    int getCourseUserRecordNum(int courseId, int userId);

    List<Integer> getCourseIdsByUserId(int userId);
}
