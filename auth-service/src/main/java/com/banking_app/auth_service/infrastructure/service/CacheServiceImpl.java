package com.banking_app.auth_service.infrastructure.service;

import com.banking_app.auth_service.application.service.CacheService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {
  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public void delete(String key) {
    this.redisTemplate.delete(key);
  }

  @Override
  public Boolean hasKey(String key) {
    return this.redisTemplate.hasKey(key);
  }

  @Override
  public Object retrieve(String key) {
    return this.redisTemplate.opsForValue().get(key);
  }

  @Override
  public void store(String key, Object value) {
    this.redisTemplate.opsForValue().set(key, value);
  }

  @Override
  public void store(String key, Object value, Integer timeOut, TimeUnit timeUnit) {
    this.redisTemplate.opsForValue().set(key, value, timeOut, timeUnit);
  }
}
