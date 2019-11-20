package com.example.springboot_demo2.timeout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DelayMsgCommand extends InnerCommand {

    @Autowired
    private RingBufferWheel ringBufferWheel;

    @Override
    void process(String msg) {

    }
}
