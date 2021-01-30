package org.nami.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * App
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Data
@TableName("app")
public class App {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String appName;

    private String description;

    private String contextPath;

    private Byte enabled;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
