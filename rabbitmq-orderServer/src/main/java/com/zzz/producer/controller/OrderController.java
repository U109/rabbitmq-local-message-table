package com.zzz.producer.controller;

import cn.hutool.core.lang.UUID;
import com.zzz.producer.entity.Order;
import com.zzz.producer.enums.OrderEnum;
import com.zzz.producer.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author zhangzhongzhen wrote on 2024/3/12
 * @version 1.0
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderService orderService;

    @GetMapping("/createOrder")
    public void createOrder() {
        Order order = new Order();
        order.setOrderSn(String.valueOf(UUID.randomUUID()));
//        order.setId(1);
        order.setOrderStatus(OrderEnum.CREATE.getCode());
        order.setMemberId(1);
        order.setMemberUsername("zhangsan");
        order.setTotalAmount(new BigDecimal(100));
        //商品ID
        order.setProductId(1);
        orderService.createOrder(order);
    }
}
