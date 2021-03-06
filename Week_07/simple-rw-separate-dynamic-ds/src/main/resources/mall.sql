create table `mall_customer`
(
    `id`             bigint       not null auto_increment comment '用户ID' primary key,
    `nickname`       varchar(40)  not null default '' comment '昵称',
    `mobile`         varchar(20)  not null default '' comment '手机号',
    `gender`         tinyint      not null default 0 comment '性别：0-未知，1-男，2-女',
    `avatar_url`     varchar(50)  not null default '' comment '头像url',
    `birthday`       datetime     not null default CURRENT_TIMESTAMP comment '生日',
    `subscribe_time` datetime     not null default CURRENT_TIMESTAMP comment '关注公众号时间',
    `is_member`      tinyint      not null default 0 comment '是否为会员：0-不是，1-是',
    `expire_time`    datetime     not null default CURRENT_TIMESTAMP comment '会员到期时间',
    `member_type`    tinyint      not null default 0 comment '会员类型：0-普通会员，1-限量会员，2-体验会员',
    `union_id`       varchar(40)  not null default '' comment '微信union_id',
    `remark`         varchar(200) not null default '' comment '备注',
    `del_flag`       tinyint      not null default 0 comment '逻辑删除标记：0-未删除，1-已删除',
    `create_time`    datetime     not null default CURRENT_TIMESTAMP comment '创建时间',
    `update_time`    datetime     not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    key              `idx_mobile` (`mobile`) using btree,
    key              `idx_create_time` (`create_time`) using btree
) engine=innodb default charset=utf8mb4 comment '用户表';

create table `mall_order`
(
    `id`                   varchar(36) not null comment '订单ID（雪花ID）' primary key,
    `customer_id`          bigint      not null comment '用户ID',
    `original_total_price` bigint      not null default 0 comment '原总价',
    `deal_total_price`     bigint      not null default 0 comment '成交总价',
    `del_flag`             tinyint     not null default 0 comment '逻辑删除标记：0-未删除，1-已删除',
    `create_time`          datetime    not null default CURRENT_TIMESTAMP comment '创建时间',
    `update_time`          datetime    not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    key                    `idx_customer_id` (`customer_id`) using btree,
    key                    `idx_create_time` (`create_time`) using btree
) engine=innodb default charset=utf8mb4 comment '订单表';

create table `mall_goods`
(
    `id`              bigint       not null auto_increment comment '商品ID' primary key,
    `name`            varchar(40)  not null default '' comment '商品名称',
    `description`     varchar(200) not null default '' comment '商品描述',
    `preview_img_url` varchar(50)  not null default '' comment '预览图url',
    `original_price`  bigint       not null default 0 comment '原始单价（为真实价格乘以1000入库，便于处理小数）',
    `stock`           bigint       not null default 0 comment '库存',
    `del_flag`        tinyint      not null default 0 comment '逻辑删除标记：0-未删除，1-已删除',
    `create_time`     datetime     not null default CURRENT_TIMESTAMP comment '创建时间',
    `update_time`     datetime     not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    key               `idx_create_time` (`create_time`) using btree
) engine=innodb default charset=utf8mb4 comment '商品表';

create table `mall_order_goods`
(
    `order_id`       varchar(36) not null comment '订单ID（雪花ID）',
    `goods_id`       bigint      not null comment '商品ID',
    `amount`         bigint      not null default 0 comment '购买数量',
    `original_price` bigint      not null default 0 comment '原单价',
    `deal_price`     bigint      not null default 0 comment '成交单价',
    `del_flag`       tinyint     not null default 0 comment '逻辑删除标记：0-未删除，1-已删除',
    `create_time`    datetime    not null default CURRENT_TIMESTAMP comment '创建时间',
    `update_time`    datetime    not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    primary key (`order_id`, `goods_id`),
    key              `idx_create_time` (`create_time`) using btree
) engine=innodb default charset=utf8mb4 comment '订单-商品 联系表';
