package com.cuiwz.service;

import com.cuiwz.pojo.TradeCoupon;

/**
 * @Author cuiwz
 * @Date 2020/4/15 23:04
 */
public interface ICouponService {

    /**
     * 根据ID查询
     * @param couponId
     * @return
     */
    TradeCoupon findOne(Long couponId);

}
