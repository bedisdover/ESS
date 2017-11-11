package cn.edu.nju.service.examService;

public class ErrorTemplateFormatException extends Exception {

    private String message;

    public ErrorTemplateFormatException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
