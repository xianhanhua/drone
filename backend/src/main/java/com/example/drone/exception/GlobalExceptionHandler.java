/**
 * 文件说明：全局异常处理类，把后端错误统一转换成接口返回格式。
 */
package com.example.drone.exception;

import java.util.stream.Collectors;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.drone.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常，例如无人机不存在、序列号重复。
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleBusinessException(BusinessException ex) {
        return ApiResponse.fail(ex.getMessage());
    }

    /**
     * 处理参数校验异常，例如必填字段为空、数字超出范围。
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleValidationException(MethodArgumentNotValidException ex) {
        // 可能有多个字段同时错误，这里把所有错误提示拼成一句话返回。
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining("；"));
        return ApiResponse.fail(message);
    }

    /**
     * 处理未登录或登录失败异常。
     */
    @ExceptionHandler({AuthenticationException.class, UnauthenticatedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Void> handleAuthenticationException(Exception ex) {
        return ApiResponse.fail("请先登录");
    }

    /**
     * 兜底异常处理。没有被上面几类捕获的错误都会走这里。
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleException(Exception ex) {
        return ApiResponse.fail("系统异常：" + ex.getMessage());
    }
}
