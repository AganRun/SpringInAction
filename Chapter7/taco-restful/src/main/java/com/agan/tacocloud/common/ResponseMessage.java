package com.agan.tacocloud.common;

import org.springframework.http.HttpStatus;

public class ResponseMessage<T> {

    private Integer code;
    private String message;
    private String detail;
    private T data;

    public ResponseMessage() {
    }

    public ResponseMessage(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 请求成功
     * @param object 返回对象
     * @param message 返回信息
     * @return ResponseMessage
     */
    public static ResponseMessage<Object> success(Object object, String message) {
        ResponseMessage<Object> responseMessage = new ResponseMessage<>();
        //此处也可以自定义一个状态码枚举类
        responseMessage.setCode(HttpStatus.OK.value());
        if (message == null) {
            responseMessage.setMessage(HttpStatus.OK.getReasonPhrase());
        }

        responseMessage.setData(object);
        return responseMessage;
    }

    public static ResponseMessage success() {
        return success(null, null);
    }

    public static ResponseMessage success(Object object) {
        return success(object, null);
    }

    public static ResponseMessage success(String message) {
        return success(null, message);
    }

    /**
     * 请求失败
     * @param code 错误码
     * @param message 错误信息
     * @return ResponseMessage
     */
    public static ResponseMessage<Object> error(Integer code, String message) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(code);
        responseMessage.setMessage(message);
        return responseMessage;
    }

    public static ResponseMessage<Object> error(Integer code) {
        return error(code, null);
    }

    public static ResponseMessage<Object> error(String message) {
        return error(null, message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
