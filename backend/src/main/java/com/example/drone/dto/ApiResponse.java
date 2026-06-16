/**
 * 文件说明：接口统一返回对象，包装 success、message 和 data。
 */
package com.example.drone.dto;

public class ApiResponse<T> {

    // 表示接口是否处理成功，前端根据它判断显示成功还是错误提示。
    private boolean success;

    // 返回给前端看的提示信息。
    private String message;

    // 真正的数据内容。T 是泛型，可以是无人机、列表，也可以为空。
    private T data;

    private ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        // 只有数据、没有特别提示时使用。
        return new ApiResponse<T>(true, "OK", data);
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        // 成功并且需要返回自定义提示时使用，例如“保存成功”。
        return new ApiResponse<T>(true, message, data);
    }

    public static <T> ApiResponse<T> fail(String message) {
        // 失败时统一返回 success=false，data 为空。
        return new ApiResponse<T>(false, message, null);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
