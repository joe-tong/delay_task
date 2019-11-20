package com.example.springboot_demo2.timeout;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class BeanConfig {

    @Bean
    public RingBufferWheel initRingBufferWheel(){
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        return new RingBufferWheel(executorService);
    }
}
