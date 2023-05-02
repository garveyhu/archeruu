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
    private String msg;
    /**
     * 总数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer total;

    /**
     * 返回数据
     */
    private T data;

    public ResultVO(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }


    public ResultVO(boolean success, int code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public ResultVO(boolean success, int code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultVO(boolean success, int code, String msg, T data, Integer total) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.total = total;
    }

    public ResultVO(boolean success, ResultCode resultCode) {
        this.success = success;
        this.msg = resultCode.getMsg();
        this.code = resultCode.getCode();
    }


    public ResultVO(boolean success, ResultCode resultCode, T data) {
        this.success = success;
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    public ResultVO(boolean success, ResultCode resultCode, T data, Integer total) {
        this.success = success;
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
        this.total = total;
    }

    public static <T> ResultVO<T> create() {
        return new ResultVO<>();
    }

    public static <T> ResultVO<T> create(boolean success, String msg) {
        return new ResultVO<>(success, msg);
    }

    public static <T> ResultVO<T> create(boolean success, String msg, T data) {
        return new ResultVO<>(success, success ? ResultCode.SUCCESS.getCode() : ResultCode.FAIL.getCode(), msg, data);
    }

    public static <T> ResultVO<T> create(boolean success, T data) {
        return new ResultVO<>(success, success ? ResultCode.SUCCESS : ResultCode.FAIL, data);
    }

    public static <T> ResultVO<T> create(boolean success, T data, Long total) {
        return new ResultVO<>(success, success ? ResultCode.SUCCESS : ResultCode.FAIL, data, total.intValue());
    }

    public static <T> ResultVO<T> create(boolean success, int code, String msg) {
        return new ResultVO<>(success, code, msg);
    }

    public static <T> ResultVO<T> create(boolean success, int code, String msg, T data) {
        return new ResultVO<>(success, code, msg, data);
    }

    public static <T> ResultVO<T> create(boolean success, int code, String msg, T data, Long total) {
        return new ResultVO<>(success, code, msg, data, total.intValue());
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
