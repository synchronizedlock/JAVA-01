package org.easley.mall.entity;

import lombok.Data;
import org.easley.mall.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 商品表
 *
 * @author Easley
 * @date 2021/2/28
 * @since 1.0
 */
@Data
public class MallGoods extends BaseEntity {

    /**
     * 商品ID（自增生成）
     */
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 预览图url
     */
    private String previewImgUrl;

    /**
     * 原始单价
     */
    private Long originalPrice;

    /**
     * 库存
     */
    private Long stock;

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
