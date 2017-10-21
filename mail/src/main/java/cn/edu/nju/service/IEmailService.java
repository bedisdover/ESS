package cn.edu.nju.service;

import cn.edu.nju.model.EmailInfo;
import cn.edu.nju.model.ResultInfo;

public interface IEmailService {

    ResultInfo sendEmail(EmailInfo info);
}
