package org.nami.vo;

import org.nami.exception.NamiException;

import java.io.Serializable;

/**
 * Result
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public class Result<T> implements Serializable {

    private int code;
    private String message;
    private T data;

    private Result() {
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success() {
        Result<T> result = new Result();
        result.setCode(200);
        result.setMessage("success");
        return result;
    }

    public static <T> Result<T> fail() {
        Result<T> result = new Result();
        result.setCode(500);
        result.setMessage("fail");
        return result;
    }

    public static <T> Result<T> error(NamiException namiException) {
        Result<T> result = new Result();
        result.setCode(namiException.getCode());
        result.setMessage(namiException.getMessage());
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
