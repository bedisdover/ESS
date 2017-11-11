package cn.edu.nju.dao.courseDAO;

public interface IUserCourseDAO {

    boolean doesUserHaveCourse(int userId, int courseId);
}
