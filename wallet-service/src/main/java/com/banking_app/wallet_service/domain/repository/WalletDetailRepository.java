package com.banking_app.wallet_service.domain.repository;

import com.banking_app.wallet_service.domain.entity.wallet_detail.WalletDetail;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletDetailRepository extends ReactiveCrudRepository<WalletDetail, Long> {}
