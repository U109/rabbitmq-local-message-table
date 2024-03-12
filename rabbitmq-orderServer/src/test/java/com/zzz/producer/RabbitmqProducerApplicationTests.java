package com.zzz.producer;

import cn.hutool.core.lang.UUID;
import com.zzz.producer.entity.Order;
import com.zzz.producer.enums.OrderEnum;
import com.zzz.producer.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;

@SpringBootTest
public class RabbitmqProducerApplicationTests {

    @Resource
    OrderMapper orderMapper;

    @Test
    public void contextLoads() throws InterruptedException {
        Order order = new Order();
        order.setOrderSn(String.valueOf(UUID.randomUUID()));
        order.setId(1);
        order.setOrderStatus(OrderEnum.CREATE.getCode());
        order.setMemberId(1);
        order.setMemberUsername("zhangsan");
        order.setTotalAmount(new BigDecimal("100.00"));
        //商品ID
        order.setProductId(1);
        System.out.println(order.getOrderSn());
        System.out.println(order.getOrderStatus());

        orderMapper.insert(order);
    }

}
