package com.necpu.service;

import com.necpu.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RedisServiceImpl implements RedisService{

    @Autowired
    private RedisUtil  redisUtil;

    @Override
    public String seckill(String userId, String goodId){
        SessionCallback sessionCallback = new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.watch(goodId + "_count");

                Object stockCnt = redisUtil.get(goodId + "_count");
                if (stockCnt == null || (int)stockCnt <= 0){
                    return false;
                }

                operations.multi();
                redisUtil.decr(goodId + "_count");
                redisUtil.set(goodId + "_" + userId, 1);

                return operations.exec();
            }
        };

        redisUtil.execute(sessionCallback);

        if (redisUtil.hasKey(goodId + "_" + userId)){
            return userId + "秒杀成功";
        }

        return userId + "秒杀失败";
    }

    public String seckill1(String userId, String goodId) {
        redisUtil.set("k_" + userId, userId + "_" + goodId);

        Date startTime = (Date)redisUtil.get(goodId + "_startTime");
        if (startTime == null || new Date().before(startTime)) {
            return "秒杀未开始";
        }

        int stockCnt = (int)redisUtil.get(goodId + "_count");
        if (stockCnt <= 0){
            return "秒杀已空";
        }

        if (redisUtil.get(goodId + "_" + userId) != null){
            return userId + "用户已秒杀过";
        }

        redisUtil.decr(goodId + "_count");
        redisUtil.set(goodId + "_" + userId, 1);

        return userId + "用户秒杀成功";
    }

    @Override
    public boolean initData(String goodsId, int stockCnt) {
        redisUtil.set(goodsId + "_count", stockCnt);

        try {
            String str = "2022-04-13 00:00:00";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(str);
            redisUtil.set(goodsId + "_startTime", date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
