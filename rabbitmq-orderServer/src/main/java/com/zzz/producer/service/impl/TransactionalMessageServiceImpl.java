package com.zzz.producer.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzz.producer.entity.Order;
import com.zzz.producer.entity.TransactionalMessage;
import com.zzz.producer.service.TransactionalMessageService;
import com.zzz.producer.mapper.TransactionalMessageMapper;
import com.zzz.producer.support.binding.DefaultDestination;
import com.zzz.producer.support.binding.ExchangeType;
import com.zzz.producer.support.message.DefaultTxMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangzhongzhen
 * @description 针对表【transactional_message(事务消息表)】的数据库操作Service实现
 * @createDate 2024-03-12 21:23:06
 */
@Service
public class TransactionalMessageServiceImpl extends ServiceImpl<TransactionalMessageMapper, TransactionalMessage>
        implements TransactionalMessageService {


    @Resource
    TransactionalMessageManagementService messageManagementService;

    @Resource
    ObjectMapper objectMapper;


    @Override
    public void sendTransactionalMessage(DefaultDestination destination, Order order) throws Exception {

        String txNo = UUID.randomUUID().toString();
        Map<String, Object> message = new HashMap<>();
        message.put("order_id", order.getId());
        message.put("product_id", order.getProductId());
        message.put("txNo", txNo);

        String content = objectMapper.writeValueAsString(message);
        DefaultTxMessage txMessage = DefaultTxMessage.builder()
                .businessKey(String.valueOf(order.getId()))
                .businessModule("CREATE_ORDER")
                .content(content)
                .build();

        TransactionalMessage record = new TransactionalMessage();
        record.setQueueName(destination.queueName());
        record.setExchangeName(destination.exchangeName());
        record.setExchangeType(destination.exchangeType().getType());
        record.setRoutingKey(destination.routingKey());
        record.setBusinessModule(txMessage.businessModule());
        record.setBusinessKey(txMessage.businessKey());
        record.setTxNo(txNo);
        // 保存事务消息记录
        messageManagementService.saveTransactionalMessageRecord(record, content);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                messageManagementService.sendMessageSync(record, content);
            }
        });

    }
}




