package cn.edu.nju.service.examService;

import cn.edu.nju.info.ResultInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public interface IStudentService {

    ResultInfo getCourseStudents(int courseId);

    ResultInfo deleteCourseStudents(int userId, int courseId, List<String> emails);

    ResultInfo uploadStudentList(int userId, int courseId, InputStream students);
}
