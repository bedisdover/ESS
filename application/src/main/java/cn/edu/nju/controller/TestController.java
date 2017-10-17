package cn.edu.nju.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    private static Logger logger = Logger.getLogger(TestController.class);

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");

        return "test application";
    }
}
