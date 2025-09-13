package com.banking_app.auth_service;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {
  Logger logger = LogManager.getLogger(AuthServiceApplication.class);

  @PostConstruct
  void log() {
    logger.info("run application james demo log4j");
  }

  public static void main(String[] args) {
    SpringApplication.run(AuthServiceApplication.class, args);
  }
}
