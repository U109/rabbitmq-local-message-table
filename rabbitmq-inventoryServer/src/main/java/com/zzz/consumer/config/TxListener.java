package com.zzz.consumer.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * @author zhangzhongzhen wrote on 2024/3/10
 * @version 1.0
 * @description:
 */
@RabbitListener(queues = {"localOrder.queue"})
@Component
public class TxListener {

    @RabbitHandler
    @Transactional
    public void messagerevice(String msg, Channel channel, Message message) throws IOException {
        try {
            System.out.println("msg : " + msg);
            //消费消息确认应答
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //重试消费，或投递到死信队列
            //注意：参数三是否运行重试，若设置为true，会出现死循环，你可以定义常量设置重试次数
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//            channel.basicAck();
            e.printStackTrace();
        }
    }
}