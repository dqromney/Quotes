package com.dqr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.dqr"})
public class QuotesApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Object[] { QuotesApplication.class }, args);
    }
}
