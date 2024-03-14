package com.zzz.producer.mapper;

import com.zzz.producer.entity.TransactionalMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
* @author zhangzhongzhen
* @description 针对表【transactional_message(事务消息表)】的数据库操作Mapper
* @createDate 2024-03-12 21:23:06
* @Entity com.zzz.producer.entity.TransactionalMessage
*/
public interface TransactionalMessageMapper extends BaseMapper<TransactionalMessage> {

    void updateStatusSelective(TransactionalMessage record);

    List<TransactionalMessage> queryPendingCompensationRecords(@Param("minScheduleTime") LocalDateTime minScheduleTime,
                                                               @Param("maxScheduleTime") LocalDateTime maxScheduleTime,
                                                               @Param("status") Integer status,
                                                               @Param("limit") int limit);
}




