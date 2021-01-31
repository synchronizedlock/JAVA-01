package org.nami.vo;

import lombok.Data;

/**
 * AppVO
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Data
public class AppVO {

    private Integer id;
    private String appName;
    private String description;
    private String contextPath;
    private Integer instanceNum;
    private Byte enabled;
    private String createTime;
    private String updateTime;
}
