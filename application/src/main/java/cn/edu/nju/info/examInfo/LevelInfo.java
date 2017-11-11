package cn.edu.nju.info.examInfo;

import cn.edu.nju.model.examModel.LevelModel;

import java.util.ArrayList;
import java.util.List;

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

    public static List<LevelModel> toModelList(List<LevelInfo> list) {
        List<LevelModel> result = new ArrayList<>(list.size());
        for (LevelInfo info : list) {
            result.add(new LevelModel(
                    info.getLevelId(), info.getCourseId(),
                    info.getLevel(), info.getExamId(),
                    info.getMark()
            ));
        }
        return result;
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
