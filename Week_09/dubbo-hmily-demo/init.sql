CREATE TABLE `account`
(
    `user_id` bigint(20) NOT NULL COMMENT '用户id',
    `usd`     decimal(20, 6) NOT NULL COMMENT '美金账户',
    `cny`     decimal(20, 6) NOT NULL COMMENT '人民币账户',
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
