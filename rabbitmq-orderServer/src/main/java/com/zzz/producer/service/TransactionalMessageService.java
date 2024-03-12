package com.zzz.producer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zzz.producer.entity.Order;
import com.zzz.producer.entity.TransactionalMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzz.producer.support.binding.DefaultDestination;
import com.zzz.producer.support.message.DefaultTxMessage;

/**
 * @author zhangzhongzhen
 * @description 针对表【transactional_message(事务消息表)】的数据库操作Service
 * @createDate 2024-03-12 21:23:06
 */
public interface TransactionalMessageService extends IService<TransactionalMessage> {

    void sendTransactionalMessage(DefaultDestination build, Order order) throws Exception;
}
