package com.cuiwz;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author cuiwz
 * @Date 2020/4/17 10:03
 */
@SpringBootApplication
@EnableDubboConfiguration
public class CouponServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponServiceApplication.class,args);
    }

}
