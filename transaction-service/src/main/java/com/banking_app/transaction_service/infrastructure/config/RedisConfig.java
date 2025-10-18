package com.banking_app.transaction_service.infrastructure.config;

import com.banking_app.transaction_service.domain.entity.transaction.Transaction;
import com.banking_app.transaction_service.domain.entity.transaction_fee.TransactionFee;
import com.banking_app.transaction_service.domain.entity.transaction_fee_detail.TransactionFeeDetail;
import com.banking_app.transaction_service.domain.entity.transaction_method.TransactionMethod;
import com.banking_app.transaction_service.domain.entity.transaction_method_detail.TransactionMethodDetail;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
  private final ReactiveRedisConnectionFactory redisShopConnectionFactory;

  @Bean
  public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(
      ReactiveRedisConnectionFactory factory) {
    ObjectMapper objectMapper =
        new ObjectMapper()
            .activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY);
    GenericJackson2JsonRedisSerializer serializer =
        new GenericJackson2JsonRedisSerializer(objectMapper);

    RedisSerializationContext<String, Object> context =
        RedisSerializationContext.<String, Object>newSerializationContext(
                new StringRedisSerializer())
            .value(serializer)
            .build();
    return new ReactiveRedisTemplate<>(factory, context);
  }

  @Bean(name = "reactiveRedisTransactionTemplate")
  public ReactiveRedisTemplate<String, Transaction> reactiveRedisTransactionTemplate() {
    RedisSerializationContext<String, Transaction> serializationContext =
        this.buildRedisSerializationContext(Transaction.class);
    return new ReactiveRedisTemplate<>(redisShopConnectionFactory, serializationContext);
  }

  @Bean(name = "reactiveRedisTransactionFeeTemplate")
  public ReactiveRedisTemplate<String, TransactionFee> reactiveRedisTransactionFeeTemplate() {
    RedisSerializationContext<String, TransactionFee> serializationContext =
        this.buildRedisSerializationContext(TransactionFee.class);
    return new ReactiveRedisTemplate<>(redisShopConnectionFactory, serializationContext);
  }

  @Bean(name = "reactiveRedisTransactionFeeDetailTemplate")
  public ReactiveRedisTemplate<String, TransactionFeeDetail>
      reactiveRedisTransactionFeeDetailTemplate() {
    RedisSerializationContext<String, TransactionFeeDetail> serializationContext =
        this.buildRedisSerializationContext(TransactionFeeDetail.class);
    return new ReactiveRedisTemplate<>(redisShopConnectionFactory, serializationContext);
  }

  @Bean(name = "reactiveRedisTransactionMethodTemplate")
  public ReactiveRedisTemplate<String, TransactionMethod> reactiveRedisTransactionMethodTemplate() {
    RedisSerializationContext<String, TransactionMethod> serializationContext =
        this.buildRedisSerializationContext(TransactionMethod.class);
    return new ReactiveRedisTemplate<>(redisShopConnectionFactory, serializationContext);
  }

  @Bean(name = "reactiveRedisTransactionMethodDetailTemplate")
  public ReactiveRedisTemplate<String, TransactionMethodDetail>
      reactiveRedisTransactionMethodDetailTemplate() {
    RedisSerializationContext<String, TransactionMethodDetail> serializationContext =
        this.buildRedisSerializationContext(TransactionMethodDetail.class);
    return new ReactiveRedisTemplate<>(redisShopConnectionFactory, serializationContext);
  }

  private <T> RedisSerializationContext<String, T> buildRedisSerializationContext(Class<T> clazz) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    Jackson2JsonRedisSerializer<T> serializer = new Jackson2JsonRedisSerializer<>(clazz);
    objectMapper.findAndRegisterModules();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    return RedisSerializationContext.<String, T>newSerializationContext(new StringRedisSerializer())
        .value(serializer)
        .hashKey(new StringRedisSerializer())
        .hashValue(serializer)
        .build();
  }
}
