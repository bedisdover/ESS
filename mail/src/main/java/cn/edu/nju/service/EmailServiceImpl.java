package cn.edu.nju.service;

import cn.edu.nju.model.EmailInfo;
import cn.edu.nju.model.ResultInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

@Service(value = "emailService")
public class EmailServiceImpl implements IEmailService {

    private static final String senderAccount;

    private static final String senderPassword;

    static {
        String account = null;
        String password = null;

        Properties properties = new Properties();
        InputStream inputStream = EmailServiceImpl.class.getClassLoader()
                .getResourceAsStream("mailSender.properties");
        try {
            properties.load(inputStream);
            account = properties.getProperty("senderAccount");
            password = properties.getProperty("senderPassword");
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(EmailServiceImpl.class).error(e);
        }

        senderAccount = account;
        senderPassword = password;
    }

    @Override
    public ResultInfo sendEmail(EmailInfo info) {
        try {
            info.setSenderName(senderAccount);
            info.setSenderPassword(senderPassword);
            info.setAddressFrom(senderAccount);

            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream("mail.properties");
            Properties props = new Properties();
            props.load(inputStream);

            Session session = Session.getDefaultInstance(props);
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            ResultInfo result = createMessage(info, message);
            if (!result.isSuccess()) {
                return result;
            }

            Transport transport = session.getTransport();
            transport.connect(info.getSenderName(), info.getSenderPassword());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            return new ResultInfo(true, "邮件发送成功", null);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            Logger.getLogger(EmailServiceImpl.class).error(e);
            return new ResultInfo(false, "系统异常", null);
        }
    }

    private ResultInfo createMessage(EmailInfo info, MimeMessage message)
            throws MessagingException, UnsupportedEncodingException {

        // set sender email address
        if (info.getAddressFrom() != null) {
            message.setFrom(new InternetAddress(
                    info.getAddressFrom(), "ESS", "UTF-8"
            ));
        }
        else {
            return new ResultInfo(false, "请设置发件人邮箱", null);
        }

        // set receivers email addresses
        if (info.getAddressTo() != null) {
            String[] addressTo = info.getAddressTo();
            int num = addressTo.length;
            InternetAddress[] addresses = new InternetAddress[num];
            for (int i = 0; i < num; ++i) {
                addresses[i] = new InternetAddress(addressTo[i], true);
            }
            message.setRecipients(
                    MimeMessage.RecipientType.TO, addresses
            );
        }
        else {
            return new ResultInfo(false, "请设置收件人邮箱", null);
        }

        // set email subject
        if (info.getSubject() != null) {
            message.setSubject(info.getSubject(), "UTF-8");
        }
        else {
            return new ResultInfo(false, "请设置邮件主题", null);
        }

        // set email content
        if (info.getContent() != null) {
            message.setContent(info.getContent(), "text/html;charset=UTF-8");
        }
        else {
            return new ResultInfo(false, "请设置邮件内容", false);
        }

        // set date
        message.setSentDate(new Date());

        // save
        message.saveChanges();

        return new ResultInfo(true, "邮件创建成功", false);
    }
}
