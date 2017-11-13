package cn.edu.nju.utils;

import cn.edu.nju.info.ResultInfo;
import cn.edu.nju.info.examInfo.ExamInfo;
import cn.edu.nju.info.examInfo.StudentInfo;
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
    private static final String subjectKey;
    private static final String contentKey;
    private static final String emailKey;

    // email of account verification
    private static final String verifyUrl;
    private static final String verifySubject;
    private static final String verifyContent;

    // email of exam notification
    private static final String noticeUrl;
    private static final String noticeSubject;
    private static final String noticeContent;

    // grade notification
    private static final String gradeSubject;
    private static final String gradeContent;

    static {
        String sendUrlTemp = null;
        String subKey = null;
        String ctnKey = null;
        String emKey = null;

        String verifyUrlTemp = null;
        String verifySubjectTemp = null;
        String verifyContentTemp = null;

        String noticeUrlTemp = null;
        String noticeSubjectTemp = null;
        String noticeContentTemp = null;

        String gradeSubjectTemp = null;
        String gradeContentTemp = null;

        Properties properties = new Properties();
        InputStream inputStream = EmailUtil.class.getClassLoader()
                .getResourceAsStream("mail.properties");
        try {
            properties.load(new InputStreamReader(inputStream, "UTF-8"));
            sendUrlTemp = properties.getProperty("sendUrl");
            subKey = properties.getProperty("subjectKey");
            ctnKey = properties.getProperty("contentKey");
            emKey = properties.getProperty("emailKey");

            verifyUrlTemp = properties.getProperty("verifyEmail.url");
            verifySubjectTemp = properties.getProperty("verifyEmail.subject");
            verifyContentTemp = properties.getProperty("verifyEmail.content");

            noticeUrlTemp = properties.getProperty("examNotification.startExamUrl");
            noticeContentTemp = properties.getProperty("examNotification.content");
            noticeSubjectTemp = properties.getProperty("examNotification.subject");

            gradeSubjectTemp = properties.getProperty("examGradeNotification.subject");
            gradeContentTemp = properties.getProperty("examGradeNotification.content");
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(EmailUtil.class).error(e);
        }

        sendUrl = sendUrlTemp;
        subjectKey = subKey;
        contentKey = ctnKey;
        emailKey = emKey;

        verifyUrl = verifyUrlTemp;
        verifySubject = verifySubjectTemp;
        verifyContent = verifyContentTemp;

        noticeUrl = noticeUrlTemp;
        noticeSubject = noticeSubjectTemp;
        noticeContent = noticeContentTemp;

        gradeSubject = gradeSubjectTemp;
        gradeContent = gradeContentTemp;
    }

    /**
     * send verify email
     * @param emailTo email destination
     * @param key unique key for verification
     * @return result
     */
    public static ResultInfo sendVerifyEmail(String emailTo, String key) {
        String newContent = verifyContent + verifyUrl + "?key=" + key;
        List<NameValuePair> data = new ArrayList <>();
        data.add(new BasicNameValuePair(emailKey, emailTo));
        data.add(new BasicNameValuePair(subjectKey, verifySubject));
        data.add(new BasicNameValuePair(contentKey, newContent));

        ResultInfo result = HttpUtil.post(sendUrl, data);
        if (!result.isSuccess()) {
            return result;
        }
        return new ResultInfo(true, "发送邮件成功,请前往邮箱进行验证", null);
    }

    public static ResultInfo sendExamNotificationEmail(ExamInfo examInfo) {
        List<StudentInfo> students = examInfo.getStudents();
        for (StudentInfo info : students) {
            String newContent = noticeContent + generateExamInfoContent(
                    examInfo.getExamId(), examInfo.getName(),
                    examInfo.getStartTime(), examInfo.getEndTime(),
                    EncryptionUtil.base64Encode(info.getEmail()),
                    EncryptionUtil.base64Encode(examInfo.getPassword())
            );

            List<NameValuePair> data = new ArrayList<>();
            data.add(new BasicNameValuePair(emailKey, info.getEmail()));
            data.add(new BasicNameValuePair(subjectKey, noticeSubject));
            data.add(new BasicNameValuePair(contentKey, newContent));

            ResultInfo result = HttpUtil.post(sendUrl, data);
            if (!result.isSuccess()) {
                return result;
            }
        }

        return new ResultInfo(true, "考试通知发送成功", null);
    }

    public static ResultInfo sendExamGrade(double mark,
                                           String email,
                                           String name,
                                           String startTime,
                                           String endTime) {
        String newContent = gradeContent + generateGradeContent(
                name, startTime, endTime, mark
        );
        List<NameValuePair> data = new ArrayList <>();
        data.add(new BasicNameValuePair(emailKey, email));
        data.add(new BasicNameValuePair(subjectKey, gradeSubject));
        data.add(new BasicNameValuePair(contentKey, newContent));

        ResultInfo result = HttpUtil.post(sendUrl, data);
        if (!result.isSuccess()) {
            return result;
        }

        return new ResultInfo(true, "成功发送成绩通知邮件", null);
    }

    private static String generateExamInfoContent(int examId,
                                                  String name,
                                                  String startTime,
                                                  String endTime,
                                                  String email,
                                                  String password) {
        String url = noticeUrl + "?email=" + email
                + "&amp;password=" + password
                + "&amp;examId=" + examId;
        return  "<br/>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;考试名称: " + name + "<br/>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;开始时间: " + startTime + "<br/>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;结束时间: " + endTime + "<br/>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;考试链接: " + url + "<br/>";

    }

    private static String generateGradeContent(String name,
                                               String startTime,
                                               String endTime,
                                               double mark) {
        return  "<br/>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;考试名称: " + name + "<br/>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;开始时间: " + startTime + "<br/>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;结束时间: " + endTime + "<br/>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;考试成绩: " + mark + "<br/>";
    }
}
