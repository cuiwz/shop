package com.cuiwz.service;

import com.cuiwz.pojo.TradeUser;

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

}
