package com.example.springboot_demo2.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class PayService2 {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final int totalNum = 100000;


    @Retryable(value = Exception.class,maxAttempts = 3,backoff = @Backoff(delay = 2000,multiplier = 1.5))
    public int minGoodsnum(int num) throws Exception{
        logger.info("minGoodsnum开始"+ LocalTime.now());
        if(num <= 0){
            throw new Exception("数量不对");
        }
        logger.info("minGoodsnum执行结束");
        return totalNum - num;
    }

    @Recover
    public int recoverPay(Exception e) throws Exception {
        logger.warn("recoverPay 减库存失败！！！");
        //记日志到数据库
        return totalNum;
    }

    /**
     * 如何异常和返回参数一样，选择最后一个执行
     * @param e
     * @return
     * @throws Exception
     */
    @Recover
    public int recoverPay2(IndexOutOfBoundsException e) throws Exception {
        logger.warn("recoverPay2 减库存失败！！！");
        //记日志到数据库
        return totalNum;
    }
}
