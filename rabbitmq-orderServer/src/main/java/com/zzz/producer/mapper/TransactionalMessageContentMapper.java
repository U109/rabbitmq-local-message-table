package com.zzz.producer.mapper;

import com.zzz.producer.entity.TransactionalMessageContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author zhangzhongzhen
* @description 针对表【transactional_message_content(事务消息内容表)】的数据库操作Mapper
* @createDate 2024-03-12 21:23:07
* @Entity com.zzz.producer.entity.TransactionalMessageContent
*/
public interface TransactionalMessageContentMapper extends BaseMapper<TransactionalMessageContent> {

    List<TransactionalMessageContent> queryByMessageIds(@Param("messageIds") String messageIds);
}




