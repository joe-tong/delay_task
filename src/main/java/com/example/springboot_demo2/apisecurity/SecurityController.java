package com.example.springboot_demo2.apisecurity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "数据加签")
@RequestMapping(value = "api")
public class SecurityController {

    @ApiOperation(value = "激活调用")
    @GetMapping("/invoke")
    public String invokeTest(){
        return "SUCCESS";
    }
}
