package com.cuiwz.service;

import com.cuiwz.entity.Result;
import com.cuiwz.pojo.TradeGoods;
import com.cuiwz.pojo.TradeGoodsNumberLog;

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

    /**
     * 扣减库存
     * @param goodsNumberLog
     * @return
     */
    Result reduceGoodsNum(TradeGoodsNumberLog goodsNumberLog);

}
