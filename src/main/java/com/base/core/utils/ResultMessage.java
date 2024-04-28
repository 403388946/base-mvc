package com.base.core.utils;

import java.io.Serializable;

/**
 * Created by YiMing on 2017-06-08.
 */
public class ResultMessage<T> implements Serializable {

    private boolean flag;//结果状态
    private String code;//状态码
    private String message;//结果通知
    private T data;

    public ResultMessage(boolean flag) {
        this.flag = flag;
    }

    public ResultMessage(boolean flag, String code) {
        this.flag = flag;
        this.code = code;
    }

    public ResultMessage(boolean flag, String code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public ResultMessage(boolean flag, String code, String message, T data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResultMessage<T> success(T data) {
        return new ResultMessage<T>(true, "success", "success", data);
    }

    public static ResultMessage<String> success() {
        return success("");
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
