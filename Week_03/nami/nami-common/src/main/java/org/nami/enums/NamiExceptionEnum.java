package org.nami.enums;

/**
 * NamiExceptionEnum
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public enum NamiExceptionEnum {

    /**
     * param error
     */
    PARAM_ERROR(1000, "param error"),

    /**
     * service not find
     */
    SERVICE_NOT_FIND(1001, "service not find,maybe not register"),

    /**
     * invalid config
     */
    CONFIG_ERROR(1002, "invalid config"),

    /**
     * userName or password error
     */
    LOGIN_ERROR(1003, "userName or password error"),

    /**
     *  not login
     */
    NOT_LOGIN(1004,"not login"),

    /**
     * token error
     */
    TOKEN_ERROR(1005,"token error");

    private Integer code;
    private String msg;

    NamiExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
