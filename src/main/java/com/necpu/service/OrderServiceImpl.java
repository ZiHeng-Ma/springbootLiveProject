package com.necpu.service;

import com.necpu.mapper.OrderMapper;
import com.necpu.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper mapper;

    @Override
    public void addOrder(Order order) {
        mapper.addOrder(order);
    }
}
