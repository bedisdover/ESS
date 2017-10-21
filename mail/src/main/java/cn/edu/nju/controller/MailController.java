package cn.edu.nju.controller;

import cn.edu.nju.model.EmailInfo;
import cn.edu.nju.model.ResultInfo;
import cn.edu.nju.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/mail")
public class MailController {

    private final IEmailService emailService;

    @Autowired
    public MailController(IEmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping(value = "/send")
    @ResponseBody
    public ResultInfo sendEmail(@ModelAttribute EmailInfo info) {
        return emailService.sendEmail(info);
    }
}
