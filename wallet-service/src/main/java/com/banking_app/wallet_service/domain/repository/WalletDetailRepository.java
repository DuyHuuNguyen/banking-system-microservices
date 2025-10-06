package com.banking_app.wallet_service.domain.repository;

import com.banking_app.wallet_service.domain.entity.wallet_detail.WalletDetail;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface WalletDetailRepository extends ReactiveCrudRepository<WalletDetail, Long> {
    @Query("""
    SELECT wd.*
    FROM wallet_detail wd
    JOIN wallet w 
    ON wd.wallet_id = w.id
    """)
    Mono<WalletDetail> findByWalletId(Long walletId);
}
