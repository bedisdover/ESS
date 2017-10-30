package cn.edu.nju.model.examModel;

import cn.edu.nju.info.examInfo.ExamInfo;

import java.util.ArrayList;
import java.util.List;

public class ExamModel {

    int examId;
    int courseId;
    int enable;
    String num;

    public ExamModel() {
    }

    public ExamModel(int examId, int courseId, int enable, String num) {
        this.examId = examId;
        this.courseId = courseId;
        this.enable = enable;
        this.num = num;
    }

    public static List<ExamInfo> toInfoList(List<ExamModel> list,
                                            List<Integer> maxNum) {
        List<ExamInfo> result = new ArrayList<>(list.size());
        for (ExamModel model : list) {
            String[] array = model.getNum().split(",");
            List<Integer> num = new ArrayList<>();
            for (String str : array) {
                num.add(Integer.parseInt(str));
            }
            result.add(new ExamInfo(
                    model.getExamId(), model.getCourseId(),
                    num, maxNum
            ));
        }
        return result;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
