package com.cuiwz.service;

import com.cuiwz.pojo.TradeGoods;

/**
 * @Author cuiwz
 * @Date 2020/4/15 22:27
 */
public interface IGoodsService {

    /**
     * 根据ID查询商品对象
     * @param goodsId
     * @return
     */
    TradeGoods findOne(Long goodsId);

}
