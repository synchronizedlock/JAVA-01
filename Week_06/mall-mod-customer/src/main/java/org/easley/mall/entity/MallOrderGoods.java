package org.easley.mall.entity;

import lombok.Data;
import org.easley.mall.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 订单-商品 联系表
 *
 * @author Easley
 * @date 2021/2/28
 * @since 1.0
 */
@Data
public class MallOrderGoods extends BaseEntity {

    /**
     * 订单ID（雪花生成）
     */
    private String orderId;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 购买数量
     */
    private Long amount;

    /**
     * 原单价
     */
    private Long originalPrice;

    /**
     * 成交单价
     */
    private Long dealPrice;

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
