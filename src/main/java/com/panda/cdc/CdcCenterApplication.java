package com.panda.cdc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author muxiaohui
 */
@SpringBootApplication(scanBasePackages = "com.panda.cdc")
@EnableDiscoveryClient
@MapperScan("com.panda.cdc.dao.*")
public class CdcCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CdcCenterApplication.class, args);
    }

}
