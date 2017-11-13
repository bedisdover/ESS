package cn.edu.nju.service.examService;

import cn.edu.nju.dao.examDAO.IStudentDAO;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.model.examModel.StudentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Service(value = "studentService")
public class StudentServiceImpl implements IStudentService {

    private final IStudentDAO studentDAO;

    @Autowired
    public StudentServiceImpl(IStudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public ResultInfo getExamStudents(int courseId) {
        List<StudentModel> list = studentDAO.getExamStudents(courseId);
        return new ResultInfo(true, "成功获取学生列表信息",
                StudentModel.toInfoList(list));
    }

}
