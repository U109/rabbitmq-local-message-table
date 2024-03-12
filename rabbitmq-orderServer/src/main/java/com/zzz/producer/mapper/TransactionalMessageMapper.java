package com.zzz.producer.mapper;

import com.zzz.producer.entity.TransactionalMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author zhangzhongzhen
* @description 针对表【transactional_message(事务消息表)】的数据库操作Mapper
* @createDate 2024-03-12 21:23:06
* @Entity com.zzz.producer.entity.TransactionalMessage
*/
public interface TransactionalMessageMapper extends BaseMapper<TransactionalMessage> {

    void updateStatusSelective(TransactionalMessage record);
}




