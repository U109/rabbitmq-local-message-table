package com.zzz.producer.schedule;

import com.zzz.producer.service.impl.TransactionalMessageManagementService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author: Zzz
 * @date: 2024/3/12 17:19
 * @description:
 */
@Slf4j
@Configuration
@EnableScheduling
public class ScheduleJobAutoConfiguration {

    @Resource
    RedissonClient redissonClient;

    @Resource
    TransactionalMessageManagementService messageManagementService;
    @Scheduled(fixedDelay = 10000)
    public void transactionalMessageCompensationTask() throws Exception {
        RLock lock = redissonClient.getLock("transactionalMessageCompensationTask");
        // 等待时间5秒,预期300秒执行完毕,这两个值需要按照实际场景定制
        boolean tryLock = lock.tryLock(5, 300, TimeUnit.SECONDS);
        if (tryLock) {
            try {
                long start = System.currentTimeMillis();
                log.info("开始执行事务消息推送补偿定时任务...");
                messageManagementService.processPendingCompensationRecords();
                long end = System.currentTimeMillis();
                long delta = end - start;
                // 以防锁过早释放
                if (delta < 5000) {
                    Thread.sleep(5000 - delta);
                }
                log.info("执行事务消息推送补偿定时任务完毕,耗时:{} ms...", end - start);
            } finally {
                lock.unlock();
            }
        }
    }

}
