package cn.edu.nju.dao;

public class DataException extends Exception {

    private String message;

    public DataException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
