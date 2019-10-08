package com.wdzg.benc;

import com.wdzg.benc.util.SnowflakeIdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@tk.mybatis.spring.annotation.MapperScan("com.wdzg.benc.mapper")
public class BencApplication {

    @Bean
    public SnowflakeIdWorker idWorker(){
        return new SnowflakeIdWorker(0,0);
    }

    public static void main(String[] args) {
        SpringApplication.run(BencApplication.class, args);
    }

}
