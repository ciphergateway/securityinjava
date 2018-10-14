package com.ciphergateway.sample.springboot;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.context.annotation.Bean;

//@SpringBootApplication
public class ExitCodeApplication {

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> 42;
    }

}
