package cn.edu.nju.controller;

import cn.edu.nju.vo.ResultInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class PaperController {

    @RequestMapping("/question/download")
    public void downloadQuestionTemplate(HttpServletRequest request,
                                         HttpServletResponse response) {
        final String fileName = "/download/questionTemplate.xlsx";
        final String filePath = request.getSession().getServletContext().getRealPath("/");
        File file = new File(filePath+fileName);
        if(!file.exists()){
            Logger.getLogger(PaperController.class).error("No such file: " + file.getName());
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
            Logger.getLogger(PaperController.class).error(e);
        }
    }

    @RequestMapping(value = "/question/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo uploadQuestions(@RequestParam int courseId,
                                      @RequestParam CommonsMultipartFile file) {
        System.out.println(courseId + ":" + file.getName());
        return null;
    }
}
