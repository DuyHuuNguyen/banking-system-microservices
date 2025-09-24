package com.banking_app.user_service.application.service;

import com.banking_app.user_service.domain.entity.user_location_detail.UserLocationDetail;
import reactor.core.publisher.Mono;

public interface UserLocationDetailService {
  Mono<UserLocationDetail> save(UserLocationDetail userLocationDetail);

  Mono<UserLocationDetail> findById(Long id);
}
