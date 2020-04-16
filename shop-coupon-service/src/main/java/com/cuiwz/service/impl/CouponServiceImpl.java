package com.cuiwz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cuiwz.costant.ShopCode;
import com.cuiwz.entity.Result;
import com.cuiwz.exception.CastException;
import com.cuiwz.mapper.TradeCouponMapper;
import com.cuiwz.pojo.TradeCoupon;
import com.cuiwz.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author cuiwz
 * @Date 2020/4/15 23:06
 */
@Component
@Service(interfaceClass = ICouponService.class)
public class CouponServiceImpl implements ICouponService {

    @Autowired
    private TradeCouponMapper couponMapper;

    @Override
    public TradeCoupon findOne(Long coupouId) {
        if (coupouId == null) {
            CastException.cast(ShopCode.SHOP_REQUEST_PARAMETER_VALID);
        }

        return couponMapper.selectByPrimaryKey(coupouId);
    }

    @Override
    @Transactional
    public Result updateCouponStatus(TradeCoupon coupon) {
        if (coupon==null||coupon.getCouponId()==null) {
            CastException.cast(ShopCode.SHOP_REQUEST_PARAMETER_VALID);
        }
        // 更新优惠券状态
        couponMapper.updateByPrimaryKey(coupon);
        return new Result(ShopCode.SHOP_SUCCESS.getSuccess(), ShopCode.SHOP_SUCCESS.getMessage());
    }

}
