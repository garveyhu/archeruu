package com.archeruu.core.config;

import com.archeruu.core.enums.ResultCode;
import com.archeruu.core.exception.CustomException;
import com.archeruu.core.vo.ResultVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 全局异常处理器
 *
 * @author Archer
 */
@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public ResultVO<?> handleException(CustomException e) {
        log.error("┗|｀O′|┛ 异常信息: ", e);
        return ResultVO.create(false, e.getResultCode().getCode(), e.getMessage());
    }

    /**
     * 参数错误异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class, HttpMessageNotReadableException.class, ConstraintViolationException.class})
    public ResultVO<?> handleException(Exception e) {
        log.error("┗|｀O′|┛ 参数错误异常:", e);
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;
            BindingResult result = validException.getBindingResult();
            StringBuilder message = new StringBuilder();
            if (result.hasErrors()) {
                List<ObjectError> errors = result.getAllErrors();
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    message.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(",");
                    log.error("┗|｀O′|┛ 请求参数错误：{},field:{},errorMessage:{}", fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
                });
                message.deleteCharAt(message.length()-1);
            }
            return ResultVO.create(false, 900, message.toString());
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            if (bindException.hasErrors()) {
                log.error("┗|｀O′|┛ 请求参数错误: {}", bindException.getAllErrors());
            }
        } else if (e instanceof ConstraintViolationException) {
            String message = e.getMessage();
            log.error("┗|｀O′|┛ 请求参数错误: {}", message);
            return ResultVO.create(false, 900, message);
        } else if (e instanceof HttpMessageNotReadableException) {
            HttpMessageNotReadableException httpMessageNotReadableException = (HttpMessageNotReadableException) e;
            String errorMsg = httpMessageNotReadableException.getMessage();
            return ResultVO.create(false, ResultCode.PARAM_TYPE_BIND_ERROR, errorMsg);
        }
        return ResultVO.create(false, ResultCode.PARAM_IS_INVALID);
    }

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Exception.class)
    public ResultVO<?> handleOtherException(Exception e) {
        log.error("┗|｀O′|┛ 不可知的异常: ", e);
        return ResultVO.create(false, ResultCode.SYSTEM_INNER_ERROR, e.getMessage());
    }
}
