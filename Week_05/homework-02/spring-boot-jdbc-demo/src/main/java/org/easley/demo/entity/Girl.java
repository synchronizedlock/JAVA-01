package org.easley.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 映射girl表
 *
 * @author Easley
 * @date 2021/2/21
 * @since 1.0
 */
@Data
@TableName("girl")
public class Girl {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String address;
    private String phone;
}
