package cn.edu.nju.model;

public class EmailInfo {

    private String addressFrom;
    private String addressTo;
    private String senderName;
    private String senderPassword;
    private String subject;
    private String content;

    public EmailInfo() {
    }

    public EmailInfo(String addressFrom, String addressTo, String senderName,
                     String senderPassword, String subject, String content) {
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.senderName = senderName;
        this.senderPassword = senderPassword;
        this.subject = subject;
        this.content = content;
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPassword() {
        return senderPassword;
    }

    public void setSenderPassword(String senderPassword) {
        this.senderPassword = senderPassword;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
