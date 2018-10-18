package com.ciphergateway.sample.springboot;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

//for test @SpringBootApplication
public class TestApplication implements ApplicationRunner {

    @Autowired
    private ApplicationContext context;

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> 42;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        testArgs(args);

        if (args.containsOption("stop") || args.containsOption("stop1")) {
            System.out.println("Shutdowning in mode#1...");
            System.exit(SpringApplication.exit(context, exitCodeGenerator()));
        } else if (args.containsOption("stop2")) {
            System.out.println("Shutdowning in mode#2...");
            System.exit(SpringApplication.exit(SpringApplication.run(ExitCodeApplication.class, args.getSourceArgs())));
        }
        initWebContext();
    }

    private void initWebContext() {
        
    }

    // java -jar my-spring-boot-6.0.0-SNAPSHOT.jar --debug --name="sijava"
    void testArgs(ApplicationArguments args) {
        System.out.println("in debug mode:" + args.containsOption("debug"));

        Set<String> optionNames = args.getOptionNames();
        for (String optionName : optionNames) {
            System.out.println(optionName + "=" + args.getOptionValues(optionName));
        }

        List<String> nonOptionArgs = args.getNonOptionArgs();
        System.out.println("nonOptionArgs=" + nonOptionArgs);
    }

}