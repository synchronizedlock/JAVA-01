package org.nami.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * AppInstance
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Data
@TableName("app_instance")
public class AppInstance {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer appId;

    private String version;

    private String ip;

    private Integer port;

    private Integer weight;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
