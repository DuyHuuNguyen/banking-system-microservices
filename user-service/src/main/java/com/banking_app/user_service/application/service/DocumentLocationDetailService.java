package com.banking_app.user_service.application.service;

import com.banking_app.user_service.domain.entity.document_location_detail.DocumentLocationDetail;
import reactor.core.publisher.Mono;

public interface DocumentLocationDetailService {
  Mono<DocumentLocationDetail> save(DocumentLocationDetail documentLocationDetail);
}
