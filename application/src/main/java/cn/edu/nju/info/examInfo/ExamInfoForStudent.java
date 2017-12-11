package cn.edu.nju.info.examInfo;

public class ExamInfoForStudent {

    private int examId;
    private String examName;
    private String courseName;
    private String startTime;
    private String endTime;
    private double sum;
    private double mark;

    public ExamInfoForStudent() {
    }

    public ExamInfoForStudent(int examId, String examName,
                              String courseName, String startTime,
                              String endTime, double sum, double mark) {
        this.examId = examId;
        this.examName = examName;
        this.courseName = courseName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sum = sum;
        this.mark = mark;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }
}
