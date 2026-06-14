package com.md.basePlatform.exception;

/**
 * 业务异常.
 */
public class BusinessException extends RuntimeException {

    private final int code;

    /**
     * 构造业务异常.
     *
     * @param code    业务状态码
     * @param message 错误信息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
