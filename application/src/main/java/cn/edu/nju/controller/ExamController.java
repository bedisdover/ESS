package cn.edu.nju.controller;

import cn.edu.nju.config.AccountConfig;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.ExamInfo;
import cn.edu.nju.service.examService.IExamService;
import cn.edu.nju.utils.HttpUtil;
import cn.edu.nju.utils.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class ExamController {

    private final IExamService examService;

    @Autowired
    public ExamController(IExamService examService) {
        this.examService = examService;
    }

    @RequestMapping(value = "/exam/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo createExam(HttpSession session,
                                 @RequestParam String examInfoStr,
                                 @RequestParam CommonsMultipartFile studentFile) {
        Integer userId = (Integer) session.getAttribute(AccountConfig.LOGIN_KEY);
        try {
            ExamInfo examInfo = JsonUtil.toObject(examInfoStr, ExamInfo.class);
            return examService.createExam(
                    userId, examInfo, studentFile == null ?
                            null : studentFile.getInputStream()
            );
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(ExamController.class).error(e);
            return new ResultInfo(false, "考生名单文件无法打开", null);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(ExamController.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @RequestMapping(value = "/exam/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updateExam(HttpSession session,
                                 @RequestParam int examId,
                                 @RequestParam String num,
                                 @RequestParam String mark) {
        Integer userId = (Integer) session.getAttribute(AccountConfig.LOGIN_KEY);
        return examService.updateExam(userId, examId, num, mark);
    }

    @RequestMapping(value = "/exam/list", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getExamList(@RequestParam int courseId) {
        return examService.getExamList(courseId);
    }

    @RequestMapping(value = "/paper/create", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo generatePaper(@RequestParam int examId) {
        return examService.generatePaper(examId);
    }

    // TODO finish it until iteration 3
    @RequestMapping(value = "/paper/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deletePaper(@RequestParam int paperId) {
        return examService.deletePaper(paperId);
    }

    @RequestMapping(value = "/student/download")
    public void downStudentTemplate(HttpServletRequest request,
                                    HttpServletResponse response) {
        HttpUtil.fileDownload("/download/studentTemplate.xls", request, response);
    }
}
