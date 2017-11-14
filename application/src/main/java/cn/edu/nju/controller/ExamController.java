package cn.edu.nju.controller;

import cn.edu.nju.config.AccountConfig;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.ExamInfo;
import cn.edu.nju.service.examService.IExamService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
                                 @RequestBody ExamInfo examInfo) {
        Integer userId = (Integer) session.getAttribute(AccountConfig.LOGIN_KEY);
        try {
            return examService.createExam(userId, examInfo);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(ExamController.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @RequestMapping(value = "/exam/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updateExam(HttpSession session,
                                 @RequestBody ExamInfo examInfo) {
        Integer userId = (Integer) session.getAttribute(AccountConfig.LOGIN_KEY);
        try {
            return examService.updateExam(userId, examInfo);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(ExamController.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @RequestMapping(value = "/exam/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteExam(HttpSession session,
                                 @RequestParam int examId) {
        Integer userId = (Integer) session.getAttribute(AccountConfig.LOGIN_KEY);
        try {
            return examService.deleteExam(userId, examId);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(ExamController.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    @RequestMapping(value = "/exam/list", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getExamList(@RequestParam int courseId) {
        return examService.getExamList(courseId);
    }

    @RequestMapping(value = "/exam/all")
    @ResponseBody
    public ResultInfo getAllExams(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(AccountConfig.LOGIN_KEY);
        return examService.getAllExams(userId);
    }

}
