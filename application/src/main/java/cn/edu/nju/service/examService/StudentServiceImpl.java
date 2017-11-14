package cn.edu.nju.service.examService;

import cn.edu.nju.dao.courseDAO.IUserCourseDAO;
import cn.edu.nju.dao.examDAO.IStudentDAO;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.StudentInfo;
import cn.edu.nju.model.examModel.StudentModel;
import cn.edu.nju.utils.EncryptionUtil;
import cn.edu.nju.utils.ExcelUtil;
import cn.edu.nju.utils.IOUtil;
import jxl.read.biff.BiffException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Service(value = "studentService")
public class StudentServiceImpl implements IStudentService {

    private final IStudentDAO studentDAO;

    private final IUserCourseDAO userCourseDAO;

    @Autowired
    public StudentServiceImpl(IStudentDAO studentDAO,
                              IUserCourseDAO userCourseDAO) {
        this.studentDAO = studentDAO;
        this.userCourseDAO = userCourseDAO;
    }

    @Override
    public ResultInfo getExamStudents(int courseId) {
        List<StudentModel> list = studentDAO.getExamStudents(courseId);
        return new ResultInfo(true, "成功获取学生列表信息",
                StudentModel.toInfoList(list));
    }

    @Override
    public ResultInfo uploadStudentList(int userId, int courseId, InputStream students) {
        if (!userCourseDAO.doesUserHaveCourse(userId, courseId)) {
            return new ResultInfo(false, "只有该门课的老师才能上传考生名单", null);
        }

        ByteArrayOutputStream bufferStream;
        try {
            bufferStream = IOUtil.toByteArrayOutputStream(students);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(StudentServiceImpl.class).error(e);
            return new ResultInfo(false, "文件读取错误", null);
        }

        byte[] data = bufferStream.toByteArray();

        InputStream stream1 = new ByteArrayInputStream(data);
        String md5Value = EncryptionUtil.md5(stream1);
        try {
            stream1.close();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(ExamServiceImpl.class).error(e);
        }

        if (!studentDAO.isStudentFileMD5Exist(md5Value)) {
            InputStream stream2 = new ByteArrayInputStream(data);
            List<StudentInfo> studentList;
            try {
                studentList = ExcelUtil.extractStudents(stream2);
                stream2.close();
            } catch (IOException | BiffException e) {
                e.printStackTrace();
                Logger.getLogger(StudentServiceImpl.class).error(e);
                return new ResultInfo(false, "文件读取错误", null);
            } catch (ErrorTemplateFormatException e) {
                return new ResultInfo(false, e.getMessage(), null);
            }

            studentList.forEach(info -> info.setCourseId(courseId));
            try {
                studentDAO.updateStudents(
                        StudentInfo.toModelList(studentList, md5Value)
                );
                return new ResultInfo(true, "成功添加考生名单", null);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.getLogger(StudentServiceImpl.class).error(e);
                return new ResultInfo(false, "系统异常", null);
            }
        }
        else {
            return new ResultInfo(false, "文件已经上传过,无须重新上传", null);
        }
    }

}
