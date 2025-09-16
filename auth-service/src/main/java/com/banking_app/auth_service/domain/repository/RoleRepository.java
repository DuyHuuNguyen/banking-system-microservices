package com.banking_app.auth_service.domain.repository;

import com.banking_app.auth_service.domain.entity.role.Role;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface RoleRepository extends ReactiveCrudRepository<Role, Long> {
    @Query(value = """
    SELECT  r.*
    FROM roles AS r
    INNER JOIN  account_roles AS ar
    ON r.id = ar.role_id
    WHERE ar.user_id =:accountId
    """)
    Flux<Role> findRolesByAccountId(Long accountId);

    Flux<Role> findByIdIsIn(List<Long> ids);
}
