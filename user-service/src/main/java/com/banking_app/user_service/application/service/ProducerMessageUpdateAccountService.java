package com.banking_app.user_service.application.service;

import com.example.dto.UpdatingAccountMessage;

public interface ProducerMessageUpdateAccountService {
  void updateInfoAccount(UpdatingAccountMessage updatingAccountMessage);
}
