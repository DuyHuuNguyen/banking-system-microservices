package com.banking_app.user_service.domain.repository;

import com.banking_app.user_service.domain.entity.personal_information.PersonalInformation;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonalInformationRepository
    extends ReactiveCrudRepository<PersonalInformation, Long> {
    @Query("""
      SELECT pi.*
      FROM personal_information pi
    JOIN users u
       ON u.personal_information_id = pi.id
       WHERE u.id  =:userId
    """)
    Mono<PersonalInformation> findByUserId(Long userId);
}
