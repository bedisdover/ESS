package cn.edu.nju.info.examInfo;

public class LevelInfo {

    private int levelId;
    private int courseId;
    private int level;
    private int examId;
    private double mark;

    public LevelInfo() {
    }

    public LevelInfo(int levelId, int courseId,
                     int level, int examId, double mark) {
        this.levelId = levelId;
        this.courseId = courseId;
        this.level = level;
        this.examId = examId;
        this.mark = mark;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
}
