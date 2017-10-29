package cn.edu.nju.controller;

import cn.edu.nju.info.ResultInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    private static Logger logger = Logger.getLogger(TestController.class);

    @RequestMapping("/test")
    @ResponseBody
    public ResultInfo test(String test, String name, String data) {
        return new ResultInfo(true, test + name, null);
    }
}
