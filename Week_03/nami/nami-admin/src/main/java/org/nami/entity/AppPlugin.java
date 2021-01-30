package org.nami.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * AppPlugin
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Data
@TableName("app_plugin")
public class AppPlugin {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer appId;

    private Integer pluginId;

    private Byte enabled;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
