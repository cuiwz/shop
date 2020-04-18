package com.cuiwz;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.cuiwz.utils.IDWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @Author cuiwz
 * @Date 2020/4/17 10:41
 */
@SpringBootApplication
@EnableDubboConfiguration
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    public IDWorker getBean() {
        return new IDWorker(1, 1);
    }

}
