package com.necpu.service;

import com.necpu.model.Goods;
import com.necpu.vo.GoodDetailVo;
import com.necpu.vo.GoodsVo;

import java.util.List;

public interface GoodsService {

    List<GoodsVo> getGoods();

    GoodDetailVo getGoodsDetail(String goodsId);
}
