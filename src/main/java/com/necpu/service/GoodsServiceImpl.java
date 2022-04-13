package com.necpu.service;

import com.necpu.mapper.GoodsMapper;
import com.necpu.mapper.SeckillGoodsMapper;
import com.necpu.model.Goods;
import com.necpu.model.SeckillGoods;
import com.necpu.vo.GoodDetailVo;
import com.necpu.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper mapper;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Override
    public List<GoodsVo> getSeckillGoods() {

        List<GoodsVo> result = new ArrayList<>();

        List<Goods> goodsList = mapper.getGoods();
        for (Goods goods : goodsList) {
            SeckillGoods seckillGoods = seckillGoodsMapper.getSecGoodsById(goods.getGoods_id());

            GoodsVo vo = new GoodsVo();
            vo.setGoodsId(goods.getGoods_id());
            vo.setName(goods.getName());
            vo.setPrice(goods.getPrice());
            vo.setImgPath(goods.getImg_path());
            vo.setSeckillPrice(seckillGoods.getSeckill_price());
            vo.setStartTime(seckillGoods.getStart_time());
            vo.setStockNum(seckillGoods.getStock_cnt());
        }

        return result;
    }

    @Override
    public void reduceStockCnt(String goodsId) {
        SeckillGoods seckillGoods = seckillGoodsMapper.getSecGoodsById(goodsId);
        seckillGoods.setStock_cnt(seckillGoods.getStock_cnt() - 1);
        seckillGoodsMapper.reduceStockCnt(seckillGoods);
    }

    @Override
    public GoodDetailVo getGoodsDetail(String goodsId) {
        Goods goods = mapper.getGoodsById(goodsId);
        SeckillGoods seckillGoods = seckillGoodsMapper.getSecGoodsById(goodsId);

        GoodDetailVo vo = new GoodDetailVo();
        vo.setName(goods.getName());
        vo.setImgPath(goods.getImg_path());
        vo.setPrice(goods.getPrice());
        vo.setStockCnt(seckillGoods.getStock_cnt());
        vo.setSeckillPrice(seckillGoods.getSeckill_price());
        vo.setEntTime(seckillGoods.getEnd_time());

        return vo;
    }

    @Override
    public List<Goods> getGoods() {
        return null;
    }
}
