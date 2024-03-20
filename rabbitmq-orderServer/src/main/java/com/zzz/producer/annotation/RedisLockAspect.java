package com.zzz.producer.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author: Zzz
 * @date: 2024/3/13 17:47
 * @description:
 */
@Aspect
@Component
public class RedisLockAspect {

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(RedisLock)")
    public void pointCut() {
    }

    @Around("pointCut() && @annotation(redisLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
        // 生成锁 key，可以基于方法名和参数生成 MD5
        String lockKey = generateLockKey(joinPoint, redisLock.value());
        // 尝试获取锁
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "获取锁 ！tryLock ： " );
            // 获取锁成功，执行方法
            return joinPoint.proceed();
        } finally {
            // 方法执行完毕，释放锁
            lock.unlock();
        }
    }

    private String generateLockKey(ProceedingJoinPoint joinPoint, String value) {
        // 这里简单实现，实际情况可能会复杂一些
        return value + joinPoint.getSignature().getName() + Arrays.toString(joinPoint.getArgs());
    }

}
