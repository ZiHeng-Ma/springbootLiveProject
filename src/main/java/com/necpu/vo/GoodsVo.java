package com.necpu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVo {

    private String name;
    private String goodsId;
    private String imgPath;
    private Double price;
    private Double seckillPrice;
    private int stockNum;
    private Date startTime;
}
