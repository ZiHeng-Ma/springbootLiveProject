package com.necpu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillGoods {

    private long id;
    private String goods_id;
    private double seckill_price;
    private int stock_cnt;
    private Date start_time;
    private Date end_time;
}
