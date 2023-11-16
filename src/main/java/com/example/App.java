package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication springBootApplication = new SpringApplication(App.class);

        springBootApplication.run(args);
    }
}
