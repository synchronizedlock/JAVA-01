package org.nami.vo;

import lombok.Data;

/**
 * InstanceVO
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Data
public class InstanceVO {

    private Integer id;
    private String appName;
    private String version;
    private String ip;
    private Integer port;
    private Integer weight;
    private String createTime;
    private String updateTime;
}
