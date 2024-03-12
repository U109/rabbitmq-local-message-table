package com.zzz.producer.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.TransportMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Zzz
 * @date: 2024/3/12 17:58
 * @description:
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        SingleServerConfig singleServerConfig = config.useSingleServer();
        //可以用"rediss://"来启用SSL连接
        singleServerConfig.setAddress("redis://117.72.32.234:6379");
        singleServerConfig.setPassword("zzz");
        singleServerConfig.setDatabase(0);
        singleServerConfig.setConnectTimeout(5000);
        singleServerConfig.setRetryAttempts(5);
        singleServerConfig.setRetryInterval(3000);
        singleServerConfig.setTimeout(10000);
        return Redisson.create(config);
    }
}
