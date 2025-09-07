package com.banking_app.user_service;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
public class UserServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  @PostConstruct
  public void init() {
    log.info("UserServiceApplication init");
    log.info("🚀 Service started");
    log.warn("⚠ Cảnh báo nè ní");
    log.error("❌ Lỗi rồi ní ơi");
  }
}
