package com.zhouwei.mybatis.service;

import com.zhouwei.mybatis.mapper.OrderItemMapper;
import com.zhouwei.mybatis.mapper.OrderMapper;
import com.zhouwei.mybatis.model.Order;
import com.zhouwei.mybatis.model.OrderItem;
import io.shardingjdbc.core.api.HintManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DemoService {
    
    @Resource
    private OrderMapper orderMapper;
    
    @Resource
    private OrderItemMapper orderItemMapper;
    
    public void demo() {
        orderMapper.createIfNotExistsTable();
        orderItemMapper.createIfNotExistsTable();
        orderMapper.truncateTable();
        orderItemMapper.truncateTable();
        List<Long> orderIds = new ArrayList<>(10);
        System.out.println("1.Insert--------------");
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setUserId(51);
            order.setStatus("INSERT_TEST");
            orderMapper.insert(order);
            long orderId = order.getOrderId();
            orderIds.add(orderId);
            
            OrderItem item = new OrderItem();
            item.setOrderId(orderId);
            item.setUserId(51);
            item.setStatus("INSERT_TEST");
            orderItemMapper.insert(item);
        }
        System.out.println(orderItemMapper.selectAll());
//        System.out.println("2.Delete--------------");
//        for (Long each : orderIds) {
//            orderMapper.delete(each);
//            orderItemMapper.delete(each);
//        }
//        System.out.println(orderItemMapper.selectAll());
//        orderItemMapper.dropTable();
//        orderMapper.dropTable();
    }

    public void hintDemo(){
        HintManager hintManager = HintManager.getInstance();
        hintManager.setMasterRouteOnly();
        System.out.println("强制读主表:"+orderItemMapper.selectAll());
        hintManager.close();
    }
}
