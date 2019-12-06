package com.example.springboot_demo2.xml;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "xml")
public class XmlResponseController {

    @GetMapping(value = "xml/response" , produces = MediaType.APPLICATION_XML_VALUE)
    public void xmlResponse(){

    }
}
