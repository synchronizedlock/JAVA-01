package org.nami.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Plugin
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Data
@TableName("plugin")
public class Plugin {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String code;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
