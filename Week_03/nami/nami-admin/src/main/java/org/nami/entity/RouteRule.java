package org.nami.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * RouteRule
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Data
@TableName("route_rule")
public class RouteRule {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer appId;

    private String version;

    private String matchObject;

    private String matchKey;

    private Byte matchMethod;

    private String matchRule;

    private Byte enabled;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
