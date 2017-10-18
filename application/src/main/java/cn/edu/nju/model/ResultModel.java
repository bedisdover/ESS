package cn.edu.nju.model;

/**
 * information required for result
 */
public class ResultModel {

    private boolean result;

    private String message;

    public ResultModel() {
    }

    public ResultModel(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
