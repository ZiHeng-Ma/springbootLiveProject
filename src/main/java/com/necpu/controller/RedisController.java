package com.necpu.controller;

import com.necpu.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("initData")
    public String initData(String goodsId, int stockCnt){
        return "success";
    }

    @GetMapping("/seckillAPI")
    public String seckillAPI(String userId, String goodsId){
        if (userId == null || goodsId == null){
            return "传入参数异常";
        }
        String result = redisService.seckill(userId, goodsId);
        return result;
    }
}
