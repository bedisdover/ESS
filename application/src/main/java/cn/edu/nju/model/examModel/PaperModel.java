package cn.edu.nju.model.examModel;

import cn.edu.nju.info.examInfo.AnsweredPaperInfo;
import cn.edu.nju.info.examInfo.AnsweredQuestion;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class PaperModel {

    private int paperId;
    private int examId;
    private String studentEmail;
    private double mark;
    private int enable;
    private String content;

    public PaperModel() {
    }

    public PaperModel(int paperId, int examId,
                      String studentEmail, double mark,
                      int enable, String content) {
        this.paperId = paperId;
        this.examId = examId;
        this.studentEmail = studentEmail;
        this.mark = mark;
        this.enable = enable;
        this.content = content;
    }

    public AnsweredPaperInfo toInfo(List<AnsweredQuestion> questions,
                                    String password) {
        return new AnsweredPaperInfo(
                paperId, examId, mark, studentEmail, password, questions
        );
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
