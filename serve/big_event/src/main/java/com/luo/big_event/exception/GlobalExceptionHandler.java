package com.luo.big_event.exception;

import com.luo.big_event.pojo.Result;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 @Valid 校验失败（对象参数校验）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("参数校验失败");
        log.warn("参数校验失败：{}", message);
        return Result.error(message);
    }

    /**
     * 处理 @Validated + @Pattern / @NotBlank 等校验失败（方法参数校验）
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(violation -> violation.getMessage())
                .findFirst()
                .orElse("参数格式不正确");
        log.warn("约束校验失败：{}", message);
        return Result.error(message);
    }

    /**
     * 处理所有其他未捕获的异常（系统未知异常）
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("系统未知异常：", e);
        return Result.error("服务器繁忙，请稍后重试");
    }
}