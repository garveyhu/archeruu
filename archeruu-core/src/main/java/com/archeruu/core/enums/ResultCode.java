package com.archeruu.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态码类
 *
 * @author Archer
 */
@AllArgsConstructor
@Getter
public enum ResultCode {

    /* 成功状态码 */
    SUCCESS(200, "操作成功！"),

    /* 失败状态码 */
    FAIL(110, "操作失败！"),

    /* 失败状态码 */
    FAILURE(111, "自定义错误消息"),

    /* 系统错误 */
    SYSTEM_INNER_ERROR(119, "系统内部错误"),
    SERVER_ERROR(120, "当前服务不可用"),

    /* 参数校检异常 */
    PARAM_IS_INVALID(900, "参数无效"),
    PARAM_TYPE_BIND_ERROR(901, "参数格式错误");


    /**
     * 状态码
     */
    private final int code;
    /**
     * 状态提示
     */
    private final String message;

    /**
     * 状态码 -> 状态码类
     *
     * @param code 状态码
     */
    public static ResultCode resultCodeByCode(int code) {
        for (ResultCode resultCode : ResultCode.values()) {
            if (resultCode.getCode() == code) {
                return resultCode;
            }
        }
        return null;
    }
}
