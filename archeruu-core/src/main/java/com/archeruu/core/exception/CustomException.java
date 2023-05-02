package com.archeruu.core.exception;

import com.archeruu.core.enums.ResultCode;
import lombok.Getter;

import java.text.MessageFormat;

/**
 * 自定义异常
 *
 * @author Archer
 */
@Getter
public class CustomException extends RuntimeException {

    /**
     * 状态码类
     */
    private final ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public CustomException(ResultCode resultCode, Object... args) {
        super(MessageFormat.format(resultCode.getMsg(), args));
        this.resultCode = resultCode;
    }

    public CustomException(ResultCode resultCode, String customMessage) {
        super(customMessage);
        this.resultCode = resultCode;
    }

}
