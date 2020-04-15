package com.cuiwz.exception;

import com.cuiwz.costant.ShopCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 异常抛出类
 */
@Slf4j
public class CastException {
    public static void cast(ShopCode shopCode) {
        log.error(shopCode.toString());
        throw new CustomerException(shopCode);
    }
}