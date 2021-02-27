package org.easley.mall.entity;

import lombok.Data;
import org.easley.mall.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author Easley
 * @date 2021/2/28
 * @since 1.0
 */
@Data
public class MallCustomer extends BaseEntity {

    /**
     * 用户ID（自增生成）
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 性别, 0:未知, 1:男, 2:女
     */
    private Integer gender;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 关注时间
     */
    private LocalDateTime subscribeTime;

    /**
     * 是否会员(0:不是会员,1:是会员)
     */
    private Integer isMember;

    /**
     * 会员到期时间
     */
    private LocalDateTime expireTime;

    /**
     * 会员类型 0:普通会员, 1:限量会员, 2:体验会员
     */
    private Integer memberType;

    /**
     * 公众号授权给第三方平台后分配的全平台唯一 id
     */
    private String unionId;

    /**
     * 备注信息
     */
    private String remark;

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
