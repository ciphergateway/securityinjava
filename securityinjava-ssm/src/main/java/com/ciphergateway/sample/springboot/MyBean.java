package com.ciphergateway.sample.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyBean implements CommandLineRunner {

    @Value("${name}")
    private String name;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Call... " + this.getClass().getName());
        System.out.println("this.name=" + this.name);
    }
}
