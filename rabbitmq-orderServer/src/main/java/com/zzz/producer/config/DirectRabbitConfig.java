package com.zzz.producer.config;

import com.zzz.producer.enums.OrderDirectEnum;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Zzz
 * @date: 2024/3/12 16:58
 * @description:
 */
@Configuration
public class DirectRabbitConfig {

    @Bean
    public Queue directEmailQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(OrderDirectEnum.ORDER_QUEUE.getName(), true);
    }

    //创建交换机
    @Bean
    public DirectExchange directOrderExchange() {
        return new DirectExchange(OrderDirectEnum.ORDER_EXCHANGE.getName(), true, false);
    }

    //绑定关系
    @Bean
    public Binding directEmailBinding() {
        return BindingBuilder.bind(directEmailQueue()).to(directOrderExchange()).with(OrderDirectEnum.ORDER_ROUTING_KEY.getName());
    }


}
