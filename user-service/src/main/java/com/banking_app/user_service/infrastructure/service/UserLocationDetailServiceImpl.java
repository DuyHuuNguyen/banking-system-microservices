package com.banking_app.user_service.infrastructure.service;

import com.banking_app.user_service.application.service.UserLocationDetailService;
import com.banking_app.user_service.domain.entity.user_location_detail.UserLocationDetail;
import com.banking_app.user_service.domain.repository.UserLocationDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserLocationDetailServiceImpl implements UserLocationDetailService {
  private final UserLocationDetailRepository userLocationDetailRepository;

  @Override
  public Mono<UserLocationDetail> save(UserLocationDetail userLocationDetail) {
    return this.userLocationDetailRepository.save(userLocationDetail);
  }
}
