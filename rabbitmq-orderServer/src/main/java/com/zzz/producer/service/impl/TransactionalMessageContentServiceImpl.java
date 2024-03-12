package com.zzz.producer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzz.producer.entity.TransactionalMessageContent;
import com.zzz.producer.service.TransactionalMessageContentService;
import com.zzz.producer.mapper.TransactionalMessageContentMapper;
import org.springframework.stereotype.Service;

/**
 * @author zhangzhongzhen
 * @description 针对表【transactional_message_content(事务消息内容表)】的数据库操作Service实现
 * @createDate 2024-03-12 21:23:07
 */
@Service
public class TransactionalMessageContentServiceImpl extends ServiceImpl<TransactionalMessageContentMapper, TransactionalMessageContent>
        implements TransactionalMessageContentService {

}




