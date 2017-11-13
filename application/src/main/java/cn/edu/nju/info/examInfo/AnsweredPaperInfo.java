package cn.edu.nju.info.examInfo;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class AnsweredPaperInfo {

    private int paperId;
    private int examId;
    private double mark;
    private String studentEmail;
    private List<AnsweredQuestion> answeredQuestions;

    public AnsweredPaperInfo() {
    }

    public AnsweredPaperInfo(int paperId, int examId,
                             double mark, String studentEmail,
                             List<AnsweredQuestion> answeredQuestions) {
        this.paperId = paperId;
        this.examId = examId;
        this.mark = mark;
        this.studentEmail = studentEmail;
        this.answeredQuestions = answeredQuestions;
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

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public List<AnsweredQuestion> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(List<AnsweredQuestion> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }
}
