package com.example.springboot_demo2.timeout;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("timeout")
@Api(tags = "超时")
public class TimeOutController {

    @Autowired
    private RingBufferWheel ringBufferWheel;


    @GetMapping("/add")
    public void addTask() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        RingBufferWheel wheel = new RingBufferWheel(executorService);

        for (int i = 0; i < 70; i++) {
            RingBufferWheel.Task task = new RingBufferWheel.Job(i);
            task.setKey(i);
            wheel.addTask(task);
        }
        for (int i = 0; i < 30; i++) {
            RingBufferWheel.Task task = new RingBufferWheel.Job(i);
            task.setKey(i);
            wheel.addTask(task);
        }
        wheel.start();
    }

    @GetMapping("/test")
    public void test() {
        String msg = "hello word 2";
        String msg2 = "hello word3 4";

        String message = msg.split(" ")[1];
        String message2 = msg2.split(" ")[1];

        Integer delayTime = Integer.valueOf(msg.split(" ")[2]);
        Integer delayTime2 = Integer.valueOf(msg2.split(" ")[2]);

        RingBufferWheel.Task task = new DelayMsgJob(message);
        task.setKey(delayTime);
        ringBufferWheel.addTask(task);
        ringBufferWheel.start();

        RingBufferWheel.Task task2 = new DelayMsgJob(message2);
        task2.setKey(delayTime2);
        ringBufferWheel.addTask(task2);
        ringBufferWheel.start();

        System.out.println(new Date());
    }

    private class DelayMsgJob extends RingBufferWheel.Task{
        private String msg;

        public DelayMsgJob(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        @Override
        public void run() {
            System.out.println(new Date());
            System.out.println("打印:    "+msg);
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
