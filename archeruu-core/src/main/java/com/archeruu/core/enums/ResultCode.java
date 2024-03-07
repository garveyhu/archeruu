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
    SUCCESS(200, "操作成功( •̀ ω •́ )✧"),

    /* 失败状态码 */
    FAIL(-1, "操作失败(╬▔皿▔)╯"),

    /* 系统错误 */
    SYSTEM_INNER_ERROR(500, "系统内部错误╰（‵□′）╯"),
    SERVER_UNAVAILABLE(503, "服务不可用￣へ￣"),

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
