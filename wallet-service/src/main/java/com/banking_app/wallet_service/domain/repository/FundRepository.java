package com.banking_app.wallet_service.domain.repository;

import com.banking_app.wallet_service.domain.entity.fund.Fund;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface FundRepository extends ReactiveCrudRepository<Fund, Long> {
  Flux<Fund> findByWalletId(Long walletId);

  @Query("""
  SELECT f
  FROM funds AS f 
  JOIN wallets AS w 
  ON f.wallet_id == w.id
  WHERE w.user_id =:userId AND f.id =:fundId
  """)
  Mono<Fund> findByUserIdAnAndFundId(Long userId, Long fundId);


  @Query("""
   SELECT f
   FROM funds AS f
   JOIN wallets AS w 
   ON f.wallet_id == w.id
   WHERE w.user_id =:userId AND w.id =:walletId
   """)
  Flux<Fund> findByUserIdAndWalletId(Long userId, Long walletId);
}
