package com.archeruu.core.vo;

import com.archeruu.core.enums.ResultCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * 统一响应结果实体类
 *
 * @author Archer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class ResultVO<T> {

    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 状态码
     */
    private int code;
    /**
     * 状态提示
     */
    private String message;
    /**
     * 总数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer total;

    /**
     * 返回数据
     */
    private T data;

    public ResultVO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }


    public ResultVO(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public ResultVO(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultVO(boolean success, int code, String message, T data, Integer total) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.total = total;
    }

    public ResultVO(boolean success, ResultCode resultCode) {
        this.success = success;
        this.message = resultCode.getMessage();
        this.code = resultCode.getCode();
    }


    public ResultVO(boolean success, ResultCode resultCode, T data) {
        this.success = success;
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public ResultVO(boolean success, ResultCode resultCode, T data, Integer total) {
        this.success = success;
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
        this.total = total;
    }

    public static <T> ResultVO<T> create() {
        return new ResultVO<>();
    }

    public static <T> ResultVO<T> create(boolean success, String message) {
        return new ResultVO<>(success, message);
    }

    public static <T> ResultVO<T> create(boolean success, String message, T data) {
        return new ResultVO<>(success, success ? ResultCode.SUCCESS.getCode() : ResultCode.FAIL.getCode(), message, data);
    }

    public static <T> ResultVO<T> create(boolean success, T data) {
        return new ResultVO<>(success, success ? ResultCode.SUCCESS : ResultCode.FAIL, data);
    }

    public static <T> ResultVO<T> create(boolean success, T data, Long total) {
        return new ResultVO<>(success, success ? ResultCode.SUCCESS : ResultCode.FAIL, data, total.intValue());
    }

    public static <T> ResultVO<T> create(boolean success, int code, String message) {
        return new ResultVO<>(success, code, message);
    }

    public static <T> ResultVO<T> create(boolean success, int code, String message, T data) {
        return new ResultVO<>(success, code, message, data);
    }

    public static <T> ResultVO<T> create(boolean success, int code, String message, T data, Long total) {
        return new ResultVO<>(success, code, message, data, total.intValue());
    }

    public static <T> ResultVO<T> create(boolean success, ResultCode resultCode) {
        return new ResultVO<>(success, resultCode);
    }

    public static <T> ResultVO<T> create(boolean success, ResultCode resultCode, T data) {
        return new ResultVO<>(success, resultCode, data);
    }

    public static <T> ResultVO<T> create(boolean success, ResultCode resultCode, T data, Long total) {
        return new ResultVO<>(success, resultCode, data, total.intValue());
    }

    public static <T> ResultVO<T> create(boolean success) {
        return new ResultVO<>(success, success ? ResultCode.SUCCESS : ResultCode.FAIL);
    }

    public static <T> ResultVO<T> ok() {
        return new ResultVO<>(true, ResultCode.SUCCESS);
    }

    public static <T> ResultVO<T> ok(T data) {
        return new ResultVO<>(true, ResultCode.SUCCESS, data);
    }

    public static <T> ResultVO<T> ok(T data, Long total) {
        return new ResultVO<>(true, ResultCode.SUCCESS, data, total.intValue());
    }

    public static <T> ResultVO<List<T>> page(PageInfo<T> pageInfo) {
        return ResultVO.ok(pageInfo.getList(), pageInfo.getTotal());
    }

    public static <T> ResultVO<T> fail(T data) {
        return new ResultVO<>(false, ResultCode.FAIL, data);
    }

    public static <T> ResultVO<T> failCode(ResultCode code) {
        return new ResultVO<>(false, code);
    }
}
