package cn.edu.nju.model;

/**
 * information required for success
 */
public class ResultModel {

    private boolean success;

    private String message;

    public ResultModel() {
    }

    public ResultModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
