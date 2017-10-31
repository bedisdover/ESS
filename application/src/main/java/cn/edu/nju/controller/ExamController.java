package cn.edu.nju.controller;

import cn.edu.nju.config.AccountConfig;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.service.examService.IExamService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;

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
                                 @RequestParam int courseId,
                                 @RequestParam String num,
                                 @RequestParam String mark) {
        Integer userId = (Integer) session.getAttribute(AccountConfig.LOGIN_KEY);
        return examService.createExam(userId, courseId, num, mark);
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
}
