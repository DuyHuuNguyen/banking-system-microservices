package com.banking_app.user_service.infrastructure.config;

import com.banking_app.user_service.domain.entity.user.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
public class RedisConfig {

  @Bean
  public ReactiveRedisTemplate<Long, User> reactiveRedisTemplate(
      ReactiveRedisConnectionFactory factory) {
    Jackson2JsonRedisSerializer<User> serializer = new Jackson2JsonRedisSerializer<>(User.class);

    RedisSerializationContext.RedisSerializationContextBuilder<Long, User> builder =
        RedisSerializationContext.newSerializationContext(serializer);

    RedisSerializationContext<Long, User> context = builder.build();

    return new ReactiveRedisTemplate<>(factory, context);
  }

  //  @Bean
  //  public ReactiveRedisTemplate<String, User> reactiveRedisTemplate(
  //          ReactiveRedisConnectionFactory connectionFactory) {
  //
  //    StringRedisSerializer keySerializer = new StringRedisSerializer();
  //    Jackson2JsonRedisSerializer<User> valueSerializer =
  //            new Jackson2JsonRedisSerializer<>(User.class);
  //
  //    RedisSerializationContext<String, User> context = RedisSerializationContext
  //            .<String, User>newSerializationContext(keySerializer)
  //            .value(valueSerializer)
  //            .build();
  //
  //    return new ReactiveRedisTemplate<>(connectionFactory, context);
  //  }
}
