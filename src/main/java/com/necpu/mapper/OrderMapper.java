package com.necpu.mapper;

import com.necpu.model.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    int addOrder(Order order);
}
