package com.cuiwz.service;

import com.cuiwz.entity.Result;
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

    /**
     * 更新优惠券状态
     * @param coupon
     * @return
     */
    Result updateCouponStatus(TradeCoupon coupon);

}
