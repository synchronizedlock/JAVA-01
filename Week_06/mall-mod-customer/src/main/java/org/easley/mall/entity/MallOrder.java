package org.easley.mall.entity;

import lombok.Data;
import org.easley.mall.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 订单表
 *
 * @author Easley
 * @date 2021/2/28
 * @since 1.0
 */
@Data
public class MallOrder extends BaseEntity {

    /**
     * 订单ID（雪花生成）
     */
    private String id;

    /**
     * 用户ID
     */
    private Long customerId;

    /**
     * 原总价
     */
    private Long originalTotalPrice;

    /**
     * 成交总价
     */
    private Long dealTotalPrice;

    /**
     * 逻辑删除标记（0-未删除，1-已删除）
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后一次变更时间
     */
    private LocalDateTime updateTime;
}
