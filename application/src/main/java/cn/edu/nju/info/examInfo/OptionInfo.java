package cn.edu.nju.info.examInfo;

public class OptionInfo {

    private int optionId;
    private String content;

    public OptionInfo() {
    }

    public OptionInfo(int optionId, String content) {
        this.optionId = optionId;
        this.content = content;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
