package cn.edu.nju.info.examInfo;

import java.util.List;

public class ExamAnalysis {

    private int examId;
    private double sum;
    private List<Double> scoreList;

    public ExamAnalysis() {
    }

    public ExamAnalysis(int examId, double sum, List<Double> scoreList) {
        this.examId = examId;
        this.sum = sum;
        this.scoreList = scoreList;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public List<Double> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Double> scoreList) {
        this.scoreList = scoreList;
    }
}
