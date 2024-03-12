package com.zzz.producer.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.TransportMode;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author: Zzz
 * @date: 2024/3/12 17:58
 * @description:
 */
@Configuration
@AutoConfigureBefore(RedissonProperties.class)
public class RedissonConfig {

    @Resource
    private RedissonProperties redissonProperties;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        SingleServerConfig singleServerConfig = config.useSingleServer();
        //可以用"redis://"来启用SSL连接
        singleServerConfig.setAddress("redis://" + redissonProperties.getSevers());
        singleServerConfig.setPassword(redissonProperties.getPassword());
        singleServerConfig.setDatabase(redissonProperties.getDatabase());
        singleServerConfig.setConnectTimeout(redissonProperties.getConnectTimeout());
        singleServerConfig.setRetryAttempts(redissonProperties.getRetryAttempts());
        singleServerConfig.setRetryInterval(redissonProperties.getRetryInterval());
        singleServerConfig.setTimeout(redissonProperties.getTimeout());
        singleServerConfig.setIdleConnectionTimeout(redissonProperties.getIdealConnectionTimeout());
        return Redisson.create(config);
    }

}
