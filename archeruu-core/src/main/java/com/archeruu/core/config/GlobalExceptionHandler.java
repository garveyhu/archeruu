package com.archeruu.core.config;

import com.archeruu.core.enums.ResultCode;
import com.archeruu.core.exception.CustomException;
import com.archeruu.core.vo.ResultVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        return ResultVO.create(false, e.getResultCode());
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
