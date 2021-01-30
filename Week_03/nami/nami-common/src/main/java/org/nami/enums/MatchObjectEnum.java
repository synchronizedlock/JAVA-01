package org.nami.enums;

/**
 * MatchObjectEnum
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public enum MatchObjectEnum {

    /**
     * DEFAULT
     */
    DEFAULT("DEFAULT", "默认"),

    /**
     * QUERY
     */
    QUERY("QUERY", "参数"),

    /**
     * HEADER
     */
    HEADER("HEADER", "头部");

    private String code;
    private String desc;

    MatchObjectEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
