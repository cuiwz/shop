package com.cuiwz.service;

import com.cuiwz.entity.Result;
import com.cuiwz.pojo.TradeOrder;

/**
 * @Author cuiwz
 * @Date 2020/4/15 22:10
 */
public interface IOrderService {

    /**
     * 确认订单
     * @param order
     * @return Result
     */
    Result confirmOrder(TradeOrder order);

}
