package com.zzz.producer.service;

import com.zzz.producer.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zhangzhongzhen
* @description 针对表【order(订单表)】的数据库操作Service
* @createDate 2024-03-12 21:23:06
*/
public interface OrderService extends IService<Order> {

    void createOrder(Order order);
}
