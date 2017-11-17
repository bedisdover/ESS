package cn.edu.nju.info.examInfo;

import java.util.List;

/**
 * Created by Jiayiwu on 17/11/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class QuestionOfCourse {

    private int num;
    private List<QuestionInfo> questionInfoList;

    public QuestionOfCourse() {
    }

    public QuestionOfCourse(int num, List<QuestionInfo> questionInfoList) {
        this.num = num;
        this.questionInfoList = questionInfoList;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<QuestionInfo> getQuestionInfoList() {
        return questionInfoList;
    }

    public void setQuestionInfoList(List<QuestionInfo> questionInfoList) {
        this.questionInfoList = questionInfoList;
    }
}
