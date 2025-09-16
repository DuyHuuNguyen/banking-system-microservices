package com.banking_app.auth_service;

import com.banking_app.auth_service.application.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceApplicationTests {

  @Autowired private RoleService roleService;

  @Test
  void contextLoads() {}

  @Test
  void loadRole() {
    this.roleService
        .findAll(0, 3)
        .switchIfEmpty(r -> new RuntimeException(" he he"))
        .doOnNext(role -> System.out.println(role.toString()))
        .subscribe();
  }
}
