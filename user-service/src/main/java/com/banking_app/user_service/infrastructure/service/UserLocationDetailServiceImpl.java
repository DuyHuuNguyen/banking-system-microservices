package com.banking_app.user_service.infrastructure.service;

import com.banking_app.user_service.application.service.UserLocationDetailService;
import com.banking_app.user_service.domain.repository.UserLocationDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLocationDetailServiceImpl implements UserLocationDetailService {
  private final UserLocationDetailRepository userLocationDetailRepository;
}
