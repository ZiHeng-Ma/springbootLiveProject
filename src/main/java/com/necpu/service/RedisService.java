package com.necpu.service;

public interface RedisService {

    String seckill(String userId, String goodId);

    boolean initData(String goodsId, int stockCnt);
}
