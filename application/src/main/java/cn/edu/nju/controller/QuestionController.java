package cn.edu.nju.controller;

import cn.edu.nju.config.AccountConfig;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.LevelInfo;
import cn.edu.nju.service.examService.IQuestionService;
import cn.edu.nju.utils.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
public class QuestionController {

    private final IQuestionService questionService;

    @Autowired
    public QuestionController(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping("/question/download")
    public void downloadQuestionTemplate(HttpServletRequest request,
                                         HttpServletResponse response) {
        HttpUtil.fileDownload("/download/questionTemplate.xls", request, response);
    }

    @RequestMapping(value = "/question/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo uploadQuestions(HttpSession session,
                                      @RequestParam int courseId,
                                      @RequestParam CommonsMultipartFile questions) {
        Integer userId = (Integer) session.getAttribute(AccountConfig.LOGIN_KEY);
        try {
            return questionService.saveQuestion(
                    userId, courseId, questions.getInputStream()
            );
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(QuestionController.class).error(e);
            return new ResultInfo(false, "文件读取错误", null);
        }
    }

    @RequestMapping(value = "/question/list")
    @ResponseBody
    public ResultInfo getAllQuestions(Integer page, Integer size) {
        return questionService.getAllQuestions(page, size);
    }

    @RequestMapping(value = "/question/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteQuestions(HttpSession session,
                                      @RequestBody List<Integer> questionIdList) {
        Integer userId = (Integer) session.getAttribute(AccountConfig.LOGIN_KEY);
        return questionService.deleteQuestions(userId, questionIdList);
    }

}
