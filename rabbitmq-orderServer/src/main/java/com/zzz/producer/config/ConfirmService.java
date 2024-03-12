package com.zzz.producer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zhangzhongzhen wrote on 2024/3/12
 * @version 1.0
 * @description:
 */
@Component
@Slf4j
public class ConfirmService implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("消息标识：" + correlationData);
        log.info("发送成功确认：" + ack);
        log.info("错误原因：" + cause);

    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.info("消息：" + returnedMessage.getMessage().toString());
        log.info("返回码：" + returnedMessage.getReplyCode());
        log.info("返回描述：" + returnedMessage.getMessage());
        log.info("交换机：" + returnedMessage.getExchange());
        log.info("路由key：" + returnedMessage.getRoutingKey());

    }
}
