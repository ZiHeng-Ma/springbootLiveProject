package com.necpu.controller;

import com.necpu.model.Goods;
import com.necpu.service.GoodsService;
import com.necpu.service.GoodsServiceImpl;
import com.necpu.vo.GoodDetailVo;
import com.necpu.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class GoodsController {

    @Autowired
    private GoodsService service;

    @GetMapping("/")
    public String list(Model model){
        List<GoodsVo> goodsList = service.getGoods();
        model.addAttribute("goodsList", goodsList);
        return "list";
    }

    @GetMapping("/goodDetail/{goodsId}")
    public String goodsDetails(Model model, @PathVariable String goodsId){
        GoodDetailVo vo = service.getGoodsDetail(goodsId);
        model.addAttribute("detail", vo);

        Date startTime = vo.getStartTime();
        Date endTime = vo.getEntTime();
        Date nowTime = new Date();

        int status;
        int remainSeconds = -1;
        if (nowTime.before(startTime)){
            status = 0;//秒杀未开始
            remainSeconds = (int)((startTime.getTime() - nowTime.getTime())/1000);
        }else if (nowTime.after(endTime)){
            status = 2;//秒杀结束
        }else {
            status = 1;//秒杀进行中
        }
        model.addAttribute("status", status);
        model.addAttribute("remainSeconds", remainSeconds);

        return "detail";
    }
}
