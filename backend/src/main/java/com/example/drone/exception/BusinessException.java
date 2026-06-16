/**
 * 文件说明：业务异常类，用于表示数据不存在、序列号重复等业务错误。
 */
package com.example.drone.exception;

public class BusinessException extends RuntimeException {

    /**
     * 业务异常用于表示“程序没坏，但用户操作不符合规则”。
     * 例如无人机不存在、序列号重复等。
     */
    public BusinessException(String message) {
        super(message);
    }
}
