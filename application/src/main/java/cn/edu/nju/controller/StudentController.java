package cn.edu.nju.controller;

import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.service.examService.IStudentService;
import cn.edu.nju.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @RequestMapping(value = "/student/exam", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getExamStudents(@RequestParam int courseId) {
        return studentService.getExamStudents(courseId);
    }

    @RequestMapping(value = "/student/download")
    public void downStudentTemplate(HttpServletRequest request,
                                    HttpServletResponse response) {
        HttpUtil.fileDownload("/download/studentTemplate.xls", request, response);
    }
}
