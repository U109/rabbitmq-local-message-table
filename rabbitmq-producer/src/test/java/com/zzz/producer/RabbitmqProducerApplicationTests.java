package com.zzz.producer;

import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RabbitmqProducerApplicationTests {

    // redisson客户端
    @Autowired
    RedissonClient redissonClient;

    @Test
    public void contextLoads() throws InterruptedException {
        RLock lock = redissonClient.getLock("anyLock");
        new Thread(() -> {
            lock.lock();

            try {
                System.out.println(Thread.currentThread().getName() + ":\t 获得锁");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + ":\t 释放锁");
                lock.unlock();
            }
        }).start();


        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + ":\t 获得锁");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + ":\t 释放锁");
                lock.unlock();
            }
        }).start();

        Thread.sleep(100000);
    }

}
