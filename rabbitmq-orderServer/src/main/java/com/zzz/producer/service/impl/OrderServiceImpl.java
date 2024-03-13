package com.zzz.producer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzz.producer.annotation.RedisLock;
import com.zzz.producer.entity.Order;
import com.zzz.producer.enums.OrderDirectEnum;
import com.zzz.producer.service.OrderService;
import com.zzz.producer.mapper.OrderMapper;
import com.zzz.producer.service.TransactionalMessageService;
import com.zzz.producer.support.binding.DefaultDestination;
import com.zzz.producer.support.binding.ExchangeType;
import com.zzz.producer.support.message.DefaultTxMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangzhongzhen
 * @description 针对表【order(订单表)】的数据库操作Service实现
 * @createDate 2024-03-12 21:23:06
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Resource
    OrderMapper orderMapper;

    @Resource
    TransactionalMessageService transactionalMessageService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    @RedisLock(value = "create-order:")
    public void createOrder(Order order) {

        log.info("执行createOrder");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        orderMapper.insert(order);

        try {
            transactionalMessageService.sendTransactionalMessage(
                    DefaultDestination.builder()
                            .exchangeName(OrderDirectEnum.ORDER_EXCHANGE.getName())
                            .queueName(OrderDirectEnum.ORDER_QUEUE.getName())
                            .routingKey(OrderDirectEnum.ORDER_ROUTING_KEY.getName())
                            .exchangeType(ExchangeType.DIRECT)
                            .build(), order
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}




