package com.banking_app.transaction_service.infrastructure.service;

import com.banking_app.transaction_service.application.dto.CreateTransactionDTO;
import com.banking_app.transaction_service.application.service.HandleTransactionProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HandleTransactionProducerServiceImpl implements HandleTransactionProducerService {

  @Override
  public Mono<Void> push(CreateTransactionDTO createTransactionDTO) {
    return null;
  }
}
