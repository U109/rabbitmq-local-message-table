package com.zzz.producer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 事务消息内容表
 * @author zhangzhongzhen
 * @TableName transactional_message_content
 */
@TableName(value ="transactional_message_content")
@Data
public class TransactionalMessageContent implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 事务消息记录ID
     */
    private Long messageId;

    /**
     * 消息内容
     */
    private String content;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}