package cn.edu.nju.model.examModel;

import cn.edu.nju.info.examInfo.LevelInfo;

import java.util.ArrayList;
import java.util.List;

public class LevelModel {

    private int levelId;
    private int courseId;
    private int level;
    private int examId;
    private double mark;

    public LevelModel() {
    }

    public LevelModel(int levelId, int courseId,
                      int level, int examId, double mark) {
        this.levelId = levelId;
        this.courseId = courseId;
        this.level = level;
        this.examId = examId;
        this.mark = mark;
    }

    public static List<LevelInfo> toInfoList(List<LevelModel> list) {
        List<LevelInfo> result = new ArrayList<>(list.size());
        for (LevelModel model : list) {
            result.add(new LevelInfo(
                    model.getLevelId(), model.getCourseId(),
                    model.getLevel(), model.getExamId(),
                    model.getMark()
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
