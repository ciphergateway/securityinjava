package com.ciphergateway.sample.springboot;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class MyController {

    //TODO @Value("${my.secret}")
    private String password;
    
    @RequestMapping("/")
    String home() {
        return "Hello World, " + password;
    }

}