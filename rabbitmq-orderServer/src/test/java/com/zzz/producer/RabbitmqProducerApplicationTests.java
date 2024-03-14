package com.zzz.producer;

import cn.hutool.core.lang.UUID;
import com.zzz.producer.entity.Order;
import com.zzz.producer.enums.OrderEnum;
import com.zzz.producer.mapper.OrderMapper;
import com.zzz.producer.service.OrderService;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
public class RabbitmqProducerApplicationTests {

    @Resource
    OrderService orderService;

    @Test
    public void contextLoads() throws InterruptedException {


        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                Order order = new Order();
                order.setOrderSn(String.valueOf(UUID.randomUUID()));
//        order.setId(1);
                order.setOrderStatus(OrderEnum.CREATE.getCode());
                order.setMemberId(1);
                order.setMemberUsername("zhangsan");
                order.setTotalAmount(new BigDecimal("100.00"));
                //商品ID
                order.setProductId(1);
                orderService.createOrder(order);
                Thread.currentThread().setName("线程--" + finalI);
            }).start();

        }


        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }

}
