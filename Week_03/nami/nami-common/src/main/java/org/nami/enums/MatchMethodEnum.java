package org.nami.enums;

import java.util.stream.Stream;

/**
 * MatchMethodEnum
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public enum MatchMethodEnum {

    /**
     * =
     */
    EQUAL((byte) 1, "="),

    /**
     * regex
     */
    REGEX((byte) 2, "regex"),

    /**
     * like
     */
    LIKE((byte) 3, "like");


    private Byte code;
    private String desc;

    MatchMethodEnum(Byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static MatchMethodEnum getByCode(Byte code) {
        return Stream.of(values()).filter(r -> r.getCode().equals(code)).findFirst().orElse(null);
    }
}
