package com.sati.web.app;

import HibernateConnector.DataBaseConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication {
    public static void main(String[] args) {
        new DataBaseConnector();
        SpringApplication.run(AppApplication.class, args);
    }
}
