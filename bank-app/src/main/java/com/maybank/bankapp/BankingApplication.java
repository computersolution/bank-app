package com.maybank.bankapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * BankingApplication class is the entry point for the application.
 */
@SpringBootApplication
public class BankingApplication {

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
    }

}

