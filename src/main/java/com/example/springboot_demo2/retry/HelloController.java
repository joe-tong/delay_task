package com.example.springboot_demo2.retry;

import io.swagger.annotations.Api;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * spring-retry
 */
@RestController
@Api(tags = "重试")
public class HelloController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PayService payService;
    @Autowired
    private PayService2 payService2;

    @GetMapping("/createOrder")
    public String createOrder(@RequestParam int num) throws Exception{
        int remainingnum = payService.minGoodsnum(num == 0 ? 1: num);
        int remainingnum2 = payService2.minGoodsnum(num == 0 ? 1: num);
        logger.info("剩余的数量==="+remainingnum);
        logger.info("剩余的数量==="+remainingnum2);
        return "库库存成功";
    }

}