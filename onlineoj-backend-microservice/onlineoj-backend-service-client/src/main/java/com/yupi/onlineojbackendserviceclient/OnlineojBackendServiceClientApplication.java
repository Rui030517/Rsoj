package com.yupi.onlineojbackendserviceclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class OnlineojBackendServiceClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineojBackendServiceClientApplication.class, args);
    }

}
