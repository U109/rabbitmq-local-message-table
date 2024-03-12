package com.zzz.producer.enums;

import com.zzz.producer.support.binding.ExchangeType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author zhangzhongzhen wrote on 2024/3/12
 * @version 1.0
 * @description:
 */
@Getter
@RequiredArgsConstructor
public enum OrderDirectEnum {

    ORDER_EXCHANGE("localOrder_exchange"),
    ORDER_QUEUE("localOrder.queue"),
    ORDER_ROUTING_KEY("localOrder.key");

    private final String name;

}
