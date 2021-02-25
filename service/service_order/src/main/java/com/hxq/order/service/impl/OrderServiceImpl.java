package com.hxq.order.service.impl;

import com.hxq.commonutils.ordervo.CourseWebVoOrder;
import com.hxq.commonutils.ordervo.UcenterMemberOrder;
import com.hxq.order.client.EduClient;
import com.hxq.order.client.UcenterClient;
import com.hxq.order.entity.Order;
import com.hxq.order.mapper.OrderMapper;
import com.hxq.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxq.order.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author 何鑫强
 * @since 2021-01-29
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private  UcenterClient ucenterClient;


    //创建订单返回订单号
    @Override
    public String createOrders(String courseId, String memberId) {
        //远程调用，获取课程相关信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        //远程调用获取用户相关信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);
        //创建Order对象，向order对象里面设置需要数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();

    }
}
