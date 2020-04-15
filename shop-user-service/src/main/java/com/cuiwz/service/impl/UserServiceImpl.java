package com.cuiwz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cuiwz.costant.ShopCode;
import com.cuiwz.exception.CastException;
import com.cuiwz.mapper.TradeUserMapper;
import com.cuiwz.pojo.TradeUser;
import com.cuiwz.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author cuiwz
 * @Date 2020/4/15 22:38
 */
@Component
@Service(interfaceClass = IUserService.class)
public class UserServiceImpl implements IUserService {

    @Autowired
    private TradeUserMapper userMapper;

    @Override
    public TradeUser findOne(Long userId) {
        if(userId == null){
            CastException.cast(ShopCode.SHOP_REQUEST_PARAMETER_VALID);
        }
        return userMapper.selectByPrimaryKey(userId);
    }

}
