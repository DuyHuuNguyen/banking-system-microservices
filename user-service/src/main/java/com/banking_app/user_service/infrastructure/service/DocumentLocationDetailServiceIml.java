package com.banking_app.user_service.infrastructure.service;

import com.banking_app.user_service.application.service.DocumentLocationDetailService;
import com.banking_app.user_service.domain.repository.DocumentLocationDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentLocationDetailServiceIml implements DocumentLocationDetailService {
  private final DocumentLocationDetailRepository documentLocationDetailRepository;
}
