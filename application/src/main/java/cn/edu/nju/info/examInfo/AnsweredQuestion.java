package cn.edu.nju.info.examInfo;

import cn.edu.nju.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiayiwu on 17/11/13.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class AnsweredQuestion {

    private QuestionInfo question;

    private List<Integer> answer;

    public AnsweredQuestion() {
    }

    public AnsweredQuestion(QuestionInfo question,
                            List<Integer> answer) {
        this.question = question;
        this.answer = answer;
    }

    public static List<AnsweredItem> toAnsweredItem(
            List<AnsweredQuestion> answeredQuestions) {
        List<AnsweredItem> result = new ArrayList<>(answeredQuestions.size());
        answeredQuestions.forEach(
                answeredQuestion -> result.add(answeredQuestion.toAnsweredItem())
        );
        return result;
    }

    public AnsweredItem toAnsweredItem() {
        return new AnsweredItem(
                question.getQuestionId(),
                StringUtil.stringify(answer, ",")
        );
    }


    public QuestionInfo getQuestion() {
        return question;
    }

    public void setQuestion(QuestionInfo question) {
        this.question = question;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }
}
