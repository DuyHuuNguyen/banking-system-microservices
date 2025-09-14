package com.banking_app.auth_service.application.service;

import com.banking_app.auth_service.application.dto.MailOTP;

public interface ProducerEmailService {
  void send(MailOTP mailOTP);
}
