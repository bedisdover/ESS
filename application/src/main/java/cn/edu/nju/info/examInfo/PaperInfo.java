package cn.edu.nju.info.examInfo;

import java.util.List;

public class PaperInfo {

    private int examId;
    private List<QuestionInfo> questions;

    public PaperInfo() {
    }

    public PaperInfo(int examId, List<QuestionInfo> questions) {
        this.examId = examId;
        this.questions = questions;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public List<QuestionInfo> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionInfo> questions) {
        this.questions = questions;
    }
}
