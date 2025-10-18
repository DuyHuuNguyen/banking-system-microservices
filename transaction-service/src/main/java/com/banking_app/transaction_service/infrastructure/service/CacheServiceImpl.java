package com.banking_app.transaction_service.infrastructure.service;

import com.banking_app.transaction_service.application.service.CacheService;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl<T> implements CacheService<T> {

  private final ReactiveRedisTemplate<String, T> reactiveRedisTemplate;

  @Override
  public Mono<Long> delete(String key) {
    return this.reactiveRedisTemplate.delete(key);
  }

  @Override
  public Mono<Boolean> hasKey(String key) {
    return this.reactiveRedisTemplate.hasKey(key);
  }

  @Override
  public Mono<T> retrieve(String key) {
    return this.reactiveRedisTemplate.opsForValue().get(key);
  }

  @Override
  public Mono<Boolean> store(String key, T value) {
    return this.reactiveRedisTemplate.opsForValue().set(key, value);
  }

  @Override
  public Mono<Boolean> store(String key, T value, Duration timeout) {
    return this.reactiveRedisTemplate.opsForValue().set(key, value, timeout);
  }
}
