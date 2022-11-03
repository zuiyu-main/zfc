package com.zuiyu.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@SpringBootApplication
public class ServerSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerSpringbootApplication.class, args);
    }

}
