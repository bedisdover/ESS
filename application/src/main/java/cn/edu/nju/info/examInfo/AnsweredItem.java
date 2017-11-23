package cn.edu.nju.info.examInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class AnsweredItem {

    private int questionId;
    private String answer;  // separated by ',' for example '1,2,3'

    public AnsweredItem() {
    }

    public AnsweredItem(int questionId, String answer) {
        this.questionId = questionId;
        this.answer = answer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Integer> getAnswerList() {
        String[] answerArray = answer.split(",");
        List<Integer> list = new ArrayList<>(answerArray.length);
        for (String answer : answerArray) {
            list.add(Integer.parseInt(answer));
        }
        return list;
    }
}
