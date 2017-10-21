package cn.edu.nju.utils;

import cn.edu.nju.vo.ResultInfo;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmailUtil {

    private static final String sendUrl;
    private static final String verifyUrl;
    private static final String subject;
    private static final String content;
    private static final String subjectKey;
    private static final String contentKey;
    private static final String emailKey;

    static {
        String sendUrlTemp = null;
        String verifyUrlTemp = null;
        String subjectTemp = null;
        String contentTemp = null;
        String subKey = null;
        String ctnKey = null;
        String emKey = null;

        Properties properties = new Properties();
        InputStream inputStream = EmailUtil.class.getClassLoader()
                .getResourceAsStream("mail.properties");
        try {
            properties.load(new InputStreamReader(inputStream, "UTF-8"));
            sendUrlTemp = properties.getProperty("sendUrl");
            verifyUrlTemp = properties.getProperty("verifyUrl");
            subjectTemp = properties.getProperty("subject");
            contentTemp = properties.getProperty("content");
            subKey = properties.getProperty("subjectKey");
            ctnKey = properties.getProperty("contentKey");
            emKey = properties.getProperty("emailKey");
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(EmailUtil.class).error(e);
        }

        sendUrl = sendUrlTemp;
        verifyUrl = verifyUrlTemp;
        subject = subjectTemp;
        content = contentTemp;
        subjectKey = subKey;
        contentKey = ctnKey;
        emailKey = emKey;
    }

    /**
     * send verify email
     * @param emailTo email destination
     * @param key unique key for verification
     * @return result
     */
    public static ResultInfo sendVerifyEmail(String emailTo, String key) {
        String newContent = content + verifyUrl + "?key=" + key;
        List<NameValuePair> data = new ArrayList <>();
        data.add(new BasicNameValuePair(emailKey, emailTo));
        data.add(new BasicNameValuePair(subjectKey, subject));
        data.add(new BasicNameValuePair(contentKey, newContent));

        ResultInfo result = HttpUtil.post(sendUrl, data);
        if (!result.isSuccess()) {
            return result;
        }
        return new ResultInfo(true, "发送邮件成功,请前往邮箱进行验证", null);
    }

}
