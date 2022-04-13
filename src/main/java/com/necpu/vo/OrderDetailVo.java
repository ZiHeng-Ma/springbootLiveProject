package com.necpu.vo;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDetailVo {

    private String name;
    private String imgPath;
    private Double price;
    private Date startTime;
    private String telephone;
    private String address;
}
