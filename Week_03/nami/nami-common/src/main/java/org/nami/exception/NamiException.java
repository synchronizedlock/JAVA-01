package org.nami.exception;

import org.nami.enums.NamiExceptionEnum;

/**
 * NamiException
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public final class NamiException extends RuntimeException {

    private Integer code;
    private String errMsg;

    public NamiException(NamiExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
        this.errMsg = exceptionEnum.getMsg();
    }

    public NamiException(String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
        this.code = 5000;
    }

    public NamiException(Integer code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }


    public Integer getCode() {
        return code;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
