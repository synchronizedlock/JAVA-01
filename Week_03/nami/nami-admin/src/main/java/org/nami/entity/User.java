package org.nami.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * User
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Data
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String userName;

    private String password;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
