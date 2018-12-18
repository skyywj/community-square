package com.carryjey.social.exception;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private int code;
    private String message;

    public ApiException(String message) {
        this.code = 201;
        this.message = message;
    }

    public ApiException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
