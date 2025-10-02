package com.banking_app.user_service.domain.repository;

import com.banking_app.user_service.domain.entity.user.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {


    @Query("""
    SELECT u
    FROM users u
    JOIN identity_document_information idi 
    ON u.identify_document_information_id = idi.id
    WHERE idi.id =:identificationDocumentInformationId
    """)
    Mono<User> findByIdentificationDocumentInformationId(Long identificationDocumentInformationId);
}
