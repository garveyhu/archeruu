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

    /* 系统错误 */
    SYSTEM_INNER_ERROR(119, "系统内部错误"),
    SERVER_ERROR(120, "当前服务不可用"),
    ;


    /**
     * 状态码
     */
    private final int code;
    /**
     * 状态提示
     */
    private final String msg;

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
