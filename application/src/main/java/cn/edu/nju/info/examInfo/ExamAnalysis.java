package cn.edu.nju.info.examInfo;

import java.util.List;

public class ExamAnalysis {

    private int examId;
    private List<Double> scoreList;

    public ExamAnalysis() {
    }

    public ExamAnalysis(int examId, List<Double> scoreList) {
        this.examId = examId;
        this.scoreList = scoreList;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public List<Double> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Double> scoreList) {
        this.scoreList = scoreList;
    }
}
