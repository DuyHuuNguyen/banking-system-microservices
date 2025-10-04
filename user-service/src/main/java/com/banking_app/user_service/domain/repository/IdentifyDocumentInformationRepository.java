package com.banking_app.user_service.domain.repository;

import com.banking_app.user_service.domain.entity.identity_document_information.IdentityDocumentInformation;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IdentifyDocumentInformationRepository
    extends ReactiveCrudRepository<IdentityDocumentInformation, Long> {

    @Query("""
    SELECT iddi.*
    FROM identity_document_information iddi
    JOIN users u
    ON u.identify_document_information_id = iddi.id
    WHERE u.id =:userId
    """)
    Mono<IdentityDocumentInformation> findByUserId(Long userId);
}
