package com.necpu.mapper;

import com.necpu.model.Goods;
import com.necpu.model.SeckillGoods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeckillGoodsMapper {

    SeckillGoods getSecGoodsById(String goodsId);

    void reduceStockCnt(SeckillGoods seckillGoods);
}
