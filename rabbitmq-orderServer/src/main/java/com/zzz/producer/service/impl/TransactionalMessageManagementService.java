package com.zzz.producer.service.impl;

import com.zzz.producer.entity.TransactionalMessage;
import com.zzz.producer.entity.TransactionalMessageContent;
import com.zzz.producer.mapper.TransactionalMessageContentMapper;
import com.zzz.producer.mapper.TransactionalMessageMapper;
import com.zzz.producer.support.message.TxMessageStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author zhangzhongzhen wrote on 2024/3/12
 * @version 1.0
 * @description:
 */
@Slf4j
@Service
public class TransactionalMessageManagementService {
    @Resource
    RabbitTemplate rabbitTemplate;
    @Resource
    TransactionalMessageMapper messageMapper;
    @Resource
    TransactionalMessageContentMapper messageContentMapper;

    @Resource
    ConfirmService confirmService;

    private static final LocalDateTime END = LocalDateTime.of(2999, 1, 1, 0, 0, 0);
    private static final long DEFAULT_INIT_BACKOFF = 10L;
    private static final int DEFAULT_BACKOFF_FACTOR = 2;
    private static final int DEFAULT_MAX_RETRY_TIMES = 5;
    private static final int LIMIT = 100;


    public void saveTransactionalMessageRecord(TransactionalMessage record, String content) {
        record.setMessageStatus(TxMessageStatus.PENDING.getStatus());
        record.setNextScheduleTime(calculateNextScheduleTime(LocalDateTime.now(), DEFAULT_INIT_BACKOFF, DEFAULT_BACKOFF_FACTOR, 0));
        record.setCurrentRetryTimes(0);
        record.setInitBackoff(DEFAULT_INIT_BACKOFF);
        record.setBackoffFactor(DEFAULT_BACKOFF_FACTOR);
        record.setMaxRetryTimes(DEFAULT_MAX_RETRY_TIMES);
        messageMapper.insert(record);

        TransactionalMessageContent messageContent = new TransactionalMessageContent();
        messageContent.setContent(content);
        messageContent.setMessageId(record.getId());
        messageContentMapper.insert(messageContent);

    }

    /**
     * 计算下一次执行时间
     *
     * @param base          基础时间
     * @param initBackoff   退避基准值
     * @param backoffFactor 退避指数
     * @param round         轮数
     * @return LocalDateTime
     */
    private LocalDateTime calculateNextScheduleTime(LocalDateTime base, long initBackoff, long backoffFactor, long round) {
        double delta = initBackoff * Math.pow(backoffFactor, round);
        return base.plusSeconds((long) delta);
    }

    public void sendMessageSync(TransactionalMessage record, String content) {
        try {
            rabbitTemplate.setConfirmCallback(confirmService);
            rabbitTemplate.setReturnsCallback(confirmService);

            rabbitTemplate.convertAndSend(record.getExchangeName(), record.getRoutingKey(), content,
                    new CorrelationData(record.getTxNo()));
            // 标记成功
            markSuccess(record);
        } catch (Exception e) {
            // 标记失败
            markFail(record, e);
        }
    }

    private void markSuccess(TransactionalMessage record) {
        log.info("发送消息成功,目标队列:{}", record.getQueueName());
        // 标记下一次执行时间为最大值
        record.setNextScheduleTime(END);
        record.setCurrentRetryTimes(record.getCurrentRetryTimes().compareTo(record.getMaxRetryTimes()) >= 0 ?
                record.getMaxRetryTimes() : record.getCurrentRetryTimes() + 1);
        record.setMessageStatus(TxMessageStatus.SUCCESS.getStatus());
        record.setEditTime(LocalDateTime.now());
        messageMapper.updateStatusSelective(record);
    }

    private void markFail(TransactionalMessage record, Exception e) {
        log.error("发送消息失败,目标队列:{}", record.getQueueName(), e);
        record.setCurrentRetryTimes(record.getCurrentRetryTimes().compareTo(record.getMaxRetryTimes()) >= 0 ?
                record.getMaxRetryTimes() : record.getCurrentRetryTimes() + 1);
        // 计算下一次的执行时间
        LocalDateTime nextScheduleTime = calculateNextScheduleTime(
                record.getNextScheduleTime(),
                record.getInitBackoff(),
                record.getBackoffFactor(),
                record.getCurrentRetryTimes()
        );
        record.setNextScheduleTime(nextScheduleTime);
        record.setMessageStatus(TxMessageStatus.FAIL.getStatus());
        record.setEditTime(LocalDateTime.now());
        messageMapper.updateStatusSelective(record);
    }

    public void processPendingCompensationRecords() {
        // 这里预防把刚保存的消息也推送了
        LocalDateTime max = LocalDateTime.now().plusSeconds(-DEFAULT_INIT_BACKOFF);
        LocalDateTime min = max.plusHours(-1);
        Map<Long, TransactionalMessage> collect = messageMapper.queryPendingCompensationRecords(min, max, TxMessageStatus.SUCCESS.getStatus(), LIMIT)
                .stream()
                .collect(Collectors.toMap(TransactionalMessage::getId, x -> x));
        if (!collect.isEmpty()) {
            StringJoiner joiner = new StringJoiner(",", "(", ")");
            collect.keySet().forEach(x -> joiner.add(x.toString()));
            messageContentMapper.queryByMessageIds(joiner.toString())
                    .forEach(item -> {
                        TransactionalMessage message = collect.get(item.getMessageId());
                        sendMessageSync(message, item.getContent());
                    });
        }
    }
}
