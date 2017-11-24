package cn.edu.nju.controller;

import cn.edu.nju.config.AccountConfig;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.service.examService.IStudentService;
import cn.edu.nju.utils.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Controller
public class StudentController {

    private final IStudentService studentService;

    @Autowired
    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(value = "/student/course/get", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getCourseStudents(@RequestParam int courseId) {
        return studentService.getCourseStudents(courseId);
    }

    @RequestMapping(value = "/student/course/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteCourseStudents(HttpSession session,
                                           @RequestParam int courseId,
                                           @RequestParam List<String> emails) {
        Integer userId = (Integer) session.getAttribute(AccountConfig.LOGIN_KEY);
        return studentService.deleteCourseStudents(userId, courseId, emails);
    }

    @RequestMapping(value = "/student/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo uploadStudentList(HttpSession session,
                                        @RequestParam int courseId,
                                        @RequestParam CommonsMultipartFile studentFile) {
        Integer userId = (Integer) session.getAttribute(AccountConfig.LOGIN_KEY);
        try {
            return studentService.uploadStudentList(
                    userId, courseId, studentFile.getInputStream()
            );
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(StudentController.class).error(e);
            return new ResultInfo(false, "文件读取错误", null);
        }
    }

    @RequestMapping(value = "/student/download")
    public void downStudentTemplate(HttpServletRequest request,
                                    HttpServletResponse response) {
        String context = request.getSession().getServletContext().getRealPath("/");
        String targetName = "download/studentTemplate.xls";
        HttpUtil.fileDownload(context + targetName, "student.xls", response);
    }
}
