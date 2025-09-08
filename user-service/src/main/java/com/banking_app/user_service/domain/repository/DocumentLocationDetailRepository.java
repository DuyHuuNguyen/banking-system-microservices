package com.banking_app.user_service.domain.repository;

import com.banking_app.user_service.domain.entity.document_location_detail.DocumentLocationDetail;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentLocationDetailRepository
    extends ReactiveCrudRepository<DocumentLocationDetail, Long> {}
