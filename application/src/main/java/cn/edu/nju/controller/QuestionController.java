package cn.edu.nju.controller;

import cn.edu.nju.config.AccountConfig;
import cn.edu.nju.config.Role;
import cn.edu.nju.service.examService.IQuestionService;
import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.service.userService.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
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
        final String fileName = "/download/questionTemplate.xlsx";
        final String filePath = request.getSession().getServletContext().getRealPath("/");
        File file = new File(filePath+fileName);
        if(!file.exists()){
            Logger.getLogger(QuestionController.class)
                    .error("No such file: " + file.getName());
            return;
        }

        try (FileInputStream fileInputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()) {

            response.setHeader("Content-Disposition", "attachment;Filename=" + fileName);
            byte[] bytes = new byte[2048];
            int len;
            while ((len = fileInputStream.read(bytes))>0){
                outputStream.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(QuestionController.class).error(e);
        }
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
