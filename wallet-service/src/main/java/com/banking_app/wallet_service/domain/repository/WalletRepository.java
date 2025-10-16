package com.banking_app.wallet_service.domain.repository;

import com.banking_app.wallet_service.domain.entity.wallet.Wallet;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface WalletRepository extends ReactiveCrudRepository<Wallet, Long> {
    @Query("""
    SELECT w
    FROM Wallet w
    WHERE w.user_id =:userId
    """)
    Flux<Wallet> findAllByUserId(Long userId);
}
