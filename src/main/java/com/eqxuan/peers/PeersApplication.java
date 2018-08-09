package com.eqxuan.peers;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.eqxuan.peers.mapper")
public class PeersApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeersApplication.class, args);
    }
}