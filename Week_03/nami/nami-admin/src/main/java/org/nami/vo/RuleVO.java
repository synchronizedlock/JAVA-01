package org.nami.vo;

import lombok.Data;

/**
 * RuleVO
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Data
public class RuleVO {

    private Integer id;
    private String name;
    private Integer appId;
    private String appName;
    private String version;
    private String matchObject;
    private String matchKey;
    private Byte matchMethod;
    private String matchRule;
    private Byte enabled;
    private Integer priority;
    private String createTime;
    private String updateTime;

    /**
     * eg: [name] = [nami]
     */
    private String matchStr;
}
