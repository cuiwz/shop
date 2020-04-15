package com.cuiwz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cuiwz.costant.ShopCode;
import com.cuiwz.exception.CastException;
import com.cuiwz.mapper.TradeGoodsMapper;
import com.cuiwz.pojo.TradeGoods;
import com.cuiwz.service.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author cuiwz
 * @Date 2020/4/15 22:31
 */
@Component
@Service(interfaceClass = IGoodsService.class)
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private TradeGoodsMapper goodsMapper;

    @Override
    public TradeGoods findOne(Long goodsId) {
        if (goodsId == null) {
            CastException.cast(ShopCode.SHOP_REQUEST_PARAMETER_VALID);
        }
        return goodsMapper.selectByPrimaryKey(goodsId);
    }

}
