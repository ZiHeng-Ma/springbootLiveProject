package com.necpu.vo;

import lombok.Data;

import java.util.Date;

@Data
public class GoodDetailVo {

    private String name;
    private String goodsId;
    private String imgPath;
    private Double price;
    private Double seckillPrice;
    private int stockCnt;
    private Date startTime;
    private Date entTime;
}
