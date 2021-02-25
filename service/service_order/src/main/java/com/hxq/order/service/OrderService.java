package com.hxq.order.service;

import com.hxq.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author 何鑫强
 * @since 2021-01-29
 */
public interface OrderService extends IService<Order> {

    //创建订单，返回订单号
    String createOrders(String courseId, String memberIdByJwtToken);
}
