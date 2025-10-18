package com.banking_app.transaction_service.application.service;

import java.time.Duration;
import reactor.core.publisher.Mono;

public interface CacheService<T> {
  Mono<Long> delete(String key);

  Mono<Boolean> hasKey(String key);

  Mono<T> retrieve(String key);

  Mono<Boolean> store(String key, T value);

  Mono<Boolean> store(String key, T value, Duration timeout);
}
