package com.necpu.controller;

import com.necpu.enums.ErrorMsg;
import com.necpu.model.Goods;
import com.necpu.model.Order;
import com.necpu.service.GoodsService;
import com.necpu.service.GoodsServiceImpl;
import com.necpu.service.OrderService;
import com.necpu.vo.GoodDetailVo;
import com.necpu.vo.GoodsVo;
import com.necpu.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class GoodsController {

    @Autowired
    private GoodsService service;

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String list(Model model){
        List<GoodsVo> goodsList = service.getSeckillGoods();
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

    @PostMapping("/toSeckill")
    public String toSeckill(Model model, String goodsId){
        GoodDetailVo vo = service.getGoodsDetail(goodsId);
        //秒杀未开始
        if (new Date().before(vo.getStartTime())){
            model.addAttribute("msg", ErrorMsg.UNSTART.getMsg());
            return "fail";
        }
        //库存已告罄
        if (vo.getStockCnt() < 1){
            model.addAttribute("msg", ErrorMsg.CLEARED.getMsg());
            return "fail";
        }

        //减库存 生成订单
        service.reduceStockCnt(goodsId);
        Order order = new Order();
        order.setOrder_id("12346");
        order.setUser_id("xxx");
        order.setGoods_id(goodsId);
        order.setAddress("home");
        order.setTelephone("110");
        orderService.addOrder(order);

        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setName(vo.getName());
        orderDetailVo.setImgPath(vo.getImgPath());
        orderDetailVo.setPrice(vo.getSeckillPrice());
        orderDetailVo.setStartTime(new Date());
        orderDetailVo.setTelephone(order.getTelephone());
        orderDetailVo.setAddress(order.getAddress());

        model.addAttribute("orderDetailVo", orderDetailVo);
        return "orderDetail";
    }
}
