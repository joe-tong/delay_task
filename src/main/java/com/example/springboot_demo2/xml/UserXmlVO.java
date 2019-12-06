package com.example.springboot_demo2.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@JacksonXmlRootElement(localName = "response")
@Data
public class UserXmlVO {

    @JacksonXmlProperty(localName = "user_name")
    private String name;

    @JacksonXmlElementWrapper(useWrapping = true)
    @JacksonXmlProperty(localName = "order_info")
    private List<OrderInfoVO> orderList;



}

