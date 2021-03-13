package org.easley.sharding.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 订单model
 *
 * @author Easley
 * @date 2021/3/13
 * @since 1.0
 */
@Data
@TableName("mall_order")
public class MallOrder {

    @TableId
    private Long id;

    private Long customerId;

    private Integer status;
}
