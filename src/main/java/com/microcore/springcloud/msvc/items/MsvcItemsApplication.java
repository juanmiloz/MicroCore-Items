package com.microcore.springcloud.msvc.items;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients //Note: We need to enable Feign clients when we are using them
@SpringBootApplication
public class MsvcItemsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcItemsApplication.class, args);
    }

}
