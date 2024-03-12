package com.zzz.producer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

/**
 * 事务消息表
 *
 * @author zhangzhongzhen
 * @TableName transactional_message
 */
@TableName(value = "transactional_message")
@Data
public class TransactionalMessage implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private LocalDateTime createTime;

    /**
     *
     */
    private LocalDateTime editTime;

    /**
     *
     */
    private Integer deleted;

    /**
     * 当前重试次数
     */
    private Integer currentRetryTimes;

    /**
     * 最大重试次数
     */
    private Integer maxRetryTimes;

    /**
     * 队列名
     */
    private String queueName;

    /**
     * 交换器名
     */
    private String exchangeName;

    /**
     * 交换类型
     */
    private String exchangeType;

    /**
     * 路由键
     */
    private String routingKey;

    /**
     * 业务模块
     */
    private String businessModule;

    /**
     * 业务键
     */
    private String businessKey;

    /**
     * 下一次调度时间
     */
    private LocalDateTime nextScheduleTime;

    /**
     * 消息状态
     */
    private Integer messageStatus;

    /**
     * 退避初始化值,单位为秒
     */
    private Long initBackoff;

    /**
     * 退避因子(也就是指数)
     */
    private Integer backoffFactor;

    /**
     * 事务序号
     */
    private String txNo;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}