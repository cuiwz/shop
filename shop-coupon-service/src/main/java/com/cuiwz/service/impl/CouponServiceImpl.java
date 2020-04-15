package com.cuiwz.service.impl;

import com.cuiwz.costant.ShopCode;
import com.cuiwz.exception.CastException;
import com.cuiwz.mapper.TradeCouponMapper;
import com.cuiwz.pojo.TradeCoupon;
import com.cuiwz.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author cuiwz
 * @Date 2020/4/15 23:06
 */
public class CouponServiceImpl implements ICouponService {

    @Autowired
    private TradeCouponMapper couponMapper;

    @Override
    public TradeCoupon findOne(Long coupouId) {
        if(coupouId==null){
            CastException.cast(ShopCode.SHOP_REQUEST_PARAMETER_VALID);
        }

        return couponMapper.selectByPrimaryKey(coupouId);
    }

}
