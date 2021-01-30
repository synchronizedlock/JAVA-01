package org.nami.enums;

/**
 * EnabledEnum
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public enum EnabledEnum {

    NOT_ENABLE((byte)0,"未启用"),

    ENABLE((byte)1,"启用");

    private Byte code;
    private String desc;

    EnabledEnum(Byte code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public Byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
