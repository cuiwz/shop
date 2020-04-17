package com.cuiwz.service;

import com.cuiwz.entity.Result;
import com.cuiwz.pojo.TradeUser;
import com.cuiwz.pojo.TradeUserMoneyLog;

/**
 * @Author cuiwz
 * @Date 2020/4/15 22:29
 */
public interface IUserService {

    /**
     * 根据I查询用户
     * @param userId
     * @return
     */
    TradeUser findOne(Long userId);

    /**
     * 更新用户余额
     * @param userMoneyLog
     * @return
     */
    Result updateMoneyPaid(TradeUserMoneyLog userMoneyLog);

}
