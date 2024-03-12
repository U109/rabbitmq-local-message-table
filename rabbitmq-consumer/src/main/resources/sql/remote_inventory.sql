create table product_inventory
(
    id          bigint auto_increment
        primary key,
    product_id  bigint not null comment '商品ID',
    quantity    int    not null comment '数量',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '商品库存表';

CREATE TABLE `transactional_message_log`
(
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    message_id  BIGINT UNSIGNED NOT NULL COMMENT '事务消息记录ID',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) COMMENT '事务消息日志表';
