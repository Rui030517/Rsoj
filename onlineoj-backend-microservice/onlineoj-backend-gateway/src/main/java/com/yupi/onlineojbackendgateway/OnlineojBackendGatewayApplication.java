package com.yupi.onlineojbackendgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class OnlineojBackendGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineojBackendGatewayApplication.class, args);
    }

}
