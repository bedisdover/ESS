package cn.edu.nju.service.examService;

import cn.edu.nju.info.ResultInfo;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public interface IStudentService {

    ResultInfo getExamStudents(int courseId);

    ResultInfo uploadStudentList(int userId, int courseId, InputStream students);
}
