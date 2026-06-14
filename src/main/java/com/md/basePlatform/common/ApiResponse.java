package com.md.basePlatform.common;

/**
 * 统一 API 响应体.
 *
 * @param <T> 数据类型
 */
public class ApiResponse<T> {

    private final int code;
    private final String message;
    private final T data;

    /**
     * 构造响应体.
     *
     * @param code    业务状态码
     * @param message 提示信息
     * @param data    响应数据
     */
    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功响应.
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return 响应体
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "success", data);
    }

    /**
     * 成功响应（自定义消息）.
     *
     * @param code    业务状态码
     * @param message 提示信息
     * @param data    响应数据
     * @param <T>     数据类型
     * @return 响应体
     */
    public static <T> ApiResponse<T> success(int code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }

    /**
     * 失败响应.
     *
     * @param code    业务状态码
     * @param message 错误信息
     * @param <T>     数据类型
     * @return 响应体
     */
    public static <T> ApiResponse<T> fail(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
