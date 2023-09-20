package com.workinandoutapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class WorkInAndOutApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkInAndOutApiApplication.class, args);
    }

}
