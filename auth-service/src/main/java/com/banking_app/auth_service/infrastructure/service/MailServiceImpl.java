package com.banking_app.auth_service.infrastructure.service;

import com.banking_app.auth_service.application.dto.MailOTP;
import com.banking_app.auth_service.application.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {
  private final JavaMailSender javaMailSender;

  @Override
  public void sendOtp(MailOTP mailOTP) {
    var mail = new SimpleMailMessage();
    mail.setTo(mailOTP.getTo());
    mail.setSubject(mailOTP.getSubject());
    mail.setText("Your OTP is: " + mailOTP.getOtp());
    javaMailSender.send(mail);
  }
}
