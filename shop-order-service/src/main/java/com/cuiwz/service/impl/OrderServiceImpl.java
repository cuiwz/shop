package com.cuiwz.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.cuiwz.costant.ShopCode;
import com.cuiwz.entity.Result;
import com.cuiwz.exception.CastException;
import com.cuiwz.mapper.TradeOrderMapper;
import com.cuiwz.pojo.*;
import com.cuiwz.service.ICouponService;
import com.cuiwz.service.IGoodsService;
import com.cuiwz.service.IOrderService;
import com.cuiwz.service.IUserService;
import com.cuiwz.utils.IDWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author cuiwz
 * @Date 2020/4/15 22:14
 */
@Slf4j
@Component
@Service(interfaceClass = IOrderService.class)
public class OrderServiceImpl implements IOrderService {

    @Reference
    private IGoodsService goodsService;

    @Reference
    private IUserService userService;

    @Reference
    private ICouponService couponService;

    @Autowired
    private TradeOrderMapper orderMapper;

    @Autowired
    private IDWorker idWorker;

    @Override
    @Transactional
    public Result confirmOrder(TradeOrder order) {
        // 1.校验订单
        checkOrder(order);
        // 2.生成预订单
        savePreOrder(order);
        try {
            // 3.扣减库存
            reduceGoodsNum(order);
            // 4.扣减优惠券
            updateCouponStatus(order);
            // 5.使用余额

            // 6.确认订单

            // 7.返回成功状态

        } catch (Exception e) {
            // 1.确认订单失败,发送消息

            // 2.返回失败状态
        }
        return null;
    }

    /**
     * 校验订单
     *
     * @param order
     */
    private void checkOrder(TradeOrder order) {
        // 1.校验订单是否存在
        if (order == null) {
            CastException.cast(ShopCode.SHOP_ORDER_INVALID);
        }
        // 2.校验订单中的商品是否存在
        TradeGoods goods = goodsService.findOne(order.getGoodsId());
        if (goods == null) {
            CastException.cast(ShopCode.SHOP_GOODS_NO_EXIST);
        }
        // 3.校验下单用户是否存在
        TradeUser user = userService.findOne(order.getUserId());
        if (user == null) {
            CastException.cast(ShopCode.SHOP_USER_NO_EXIST);
        }
        // 4.校验商品单价是否合法
        if (order.getGoodsPrice().compareTo(goods.getGoodsPrice()) != 0) {
            CastException.cast(ShopCode.SHOP_GOODS_PRICE_INVALID);
        }
        // 5.校验订单商品数量是否合法
        if (order.getGoodsNumber() >= goods.getGoodsNumber()) {
            CastException.cast(ShopCode.SHOP_GOODS_NUM_NOT_ENOUGH);
        }

        log.info("校验订单通过");

    }

    /**
     * 生成预订单
     * @param order
     * @return
     */
    private Long savePreOrder(TradeOrder order) {
        // 1. 设置订单状态为不可见
        order.setOrderStatus(ShopCode.SHOP_ORDER_NO_CONFIRM.getCode());
        // 2. 设置订单ID
        long orderId = idWorker.nextId();
        order.setOrderId(orderId);
        // 3. 核算订单运费
        BigDecimal shippingFee = calculateShippingFee(order.getOrderAmount());
        if (order.getShippingFee().compareTo(shippingFee) != 0) {
            CastException.cast(ShopCode.SHOP_ORDER_SHIPPINGFEE_INVALID);
        }
        // 4. 核算订单总金额是否合法
        BigDecimal orderAmount = order.getGoodsPrice().multiply(new BigDecimal(order.getGoodsNumber()));
        orderAmount.add(shippingFee);
        if (order.getOrderAmount().compareTo(orderAmount) != 0) {
            CastException.cast(ShopCode.SHOP_ORDERAMOUNT_INVALID);
        }
        // 5.判断用户是否使用余额
        BigDecimal moneyPaid = order.getMoneyPaid();
        if (moneyPaid != null) {
            // 5.1 订单中余额是否合法
            int r = moneyPaid.compareTo(BigDecimal.ZERO);

            // 余额小于0
            if (r == -1) {
                CastException.cast(ShopCode.SHOP_MONEY_PAID_LESS_ZERO);
            }

            // 余额大于0
            if (r == 1) {
                TradeUser user = userService.findOne(order.getUserId());

                if (moneyPaid.compareTo(new BigDecimal(user.getUserMoney())) == 1) {
                    CastException.cast(ShopCode.SHOP_MONEY_PAID_INVALID);
                }
            }

        } else {
            order.setMoneyPaid(BigDecimal.ZERO);
        }
        // 6.判断用户是否使用优惠券
        Long couponId = order.getCouponId();
        if (couponId != null) {
            TradeCoupon coupon = couponService.findOne(couponId);
            // 6.1 判断优惠券是否存在
            if (coupon == null) {
                CastException.cast(ShopCode.SHOP_COUPON_NO_EXIST);
            }
            // 6.2 判断优惠券是否已经被使用
            if (coupon.getIsUsed().intValue() == ShopCode.SHOP_COUPON_ISUSED.getCode().intValue()) {
                CastException.cast(ShopCode.SHOP_COUPON_ISUSED);
            }

            order.setCouponPaid(coupon.getCouponPrice());

        } else {
            order.setCouponPaid(BigDecimal.ZERO);
        }
        // 7.核算订单支付金额 订单总金额-余额-优惠券金额
        BigDecimal payAmount = order.getOrderAmount().subtract(order.getMoneyPaid()).subtract(order.getCouponPaid());
        order.setPayAmount(payAmount);
        // 8.设置下单时间
        order.setAddTime(new Date());
        // 9.保存订单到数据库
        orderMapper.insert(order);
        // 10.返回订单ID
        return orderId;
    }

    /**
     * 核算运费
     * @param orderAmount
     * @return
     */
    private BigDecimal calculateShippingFee(BigDecimal orderAmount) {
        if(orderAmount.compareTo(new BigDecimal(100)) == 1){
            return BigDecimal.ZERO;
        } else {
            return new BigDecimal(10);
        }
    }

    /**
     * 扣减库存
     * @param order
     */
    private void reduceGoodsNum(TradeOrder order) {
        TradeGoodsNumberLog goodsNumberLog = new TradeGoodsNumberLog();
        goodsNumberLog.setOrderId(order.getOrderId());
        goodsNumberLog.setGoodsId(order.getGoodsId());
        goodsNumberLog.setGoodsNumber(order.getGoodsNumber());
        Result result = goodsService.reduceGoodsNum(goodsNumberLog);
        if (result.getSuccess().equals(ShopCode.SHOP_FAIL.getSuccess())) {
            CastException.cast(ShopCode.SHOP_REDUCE_GOODS_NUM_FAIL);
        }
        log.info("订单: " + order.getOrderId() + "扣减库存成功");
    }

    /**
     * 使用优惠券
     * @param order
     */
    private void updateCouponStatus(TradeOrder order) {
        if (order.getCouponId() != null) {
            TradeCoupon coupon = couponService.findOne(order.getCouponId());
            coupon.setOrderId(order.getOrderId());
            coupon.setIsUsed(ShopCode.SHOP_COUPON_ISUSED.getCode());
            coupon.setUsedTime(new Date());

            // 更新优惠券状态
            Result result = couponService.updateCouponStatus(coupon);
            if (result.getSuccess().equals(ShopCode.SHOP_FAIL.getSuccess())) {
                CastException.cast(ShopCode.SHOP_COUPON_USE_FAIL);
            }
            log.info("订单:"+order.getOrderId()+",使用优惠券");
        }
    }

}
