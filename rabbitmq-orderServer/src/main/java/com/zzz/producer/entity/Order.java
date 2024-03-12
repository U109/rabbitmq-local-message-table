package com.zzz.producer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 订单表
 * @author zhangzhongzhen
 * @TableName order
 */
@TableName(value ="local_order")
@Data
public class Order implements Serializable {
    /**
     * 订单ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 会员id
     */
    private Integer memberId;

    /**
     * 用户帐号
     */
    private String memberUsername;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}