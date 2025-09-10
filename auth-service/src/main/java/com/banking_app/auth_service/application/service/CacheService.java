package com.banking_app.auth_service.application.service;

import java.util.concurrent.TimeUnit;

public interface CacheService {
  void delete(String key);

  Boolean hasKey(String key);

  Object retrieve(String key);

  void store(String key, Object value);

  void store(String key, Object value, Integer timeOut, TimeUnit timeUnit);
}
