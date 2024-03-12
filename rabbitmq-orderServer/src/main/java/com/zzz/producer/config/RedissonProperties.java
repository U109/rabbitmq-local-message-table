package com.zzz.producer.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangzhongzhen wrote on 2024/3/12
 * @version 1.0
 * @description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.redisson")
public class RedissonProperties {

    @Value("${spring.redisson.severs}")
    private String severs;

    @Value("${spring.redisson.password}")
    private String password;

    @Value("${spring.redisson.database:0}")
    private int database;

    @Value("${spring.redisson.maxWaitTime:1500}")
    private int maxWaitTime = 1500;

    @Value("${spring.redisson.idealConnectionTimeout:10000}")
    private int idealConnectionTimeout = 10000;

    @Value("${spring.redisson.retryInterval:1000}")
    private int retryInterval = 1000;

    @Value("${spring.redisson.timeout:10000}")
    private int timeout;

    @Value("${spring.redisson.connectTimeout:10000}")
    private int connectTimeout;

    @Value("${spring.redisson.maxActive:1400}")
    private int maxActive;

    @Value("${spring.redisson.retryAttempts:3}")
    private int retryAttempts;


}
