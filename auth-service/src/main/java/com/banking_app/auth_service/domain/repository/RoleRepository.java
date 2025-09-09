package com.banking_app.auth_service.domain.repository;

import com.banking_app.auth_service.domain.entity.role.Role;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RoleRepository extends ReactiveCrudRepository<Role, Long> {
    @Query(value = """
    SELECT  r.*
    FROM roles AS r
    INNER JOIN  role_users AS ru
    ON r.id = ru.role_id
    WHERE ru.account_id =:accountId
    """)
    Flux<Role> findRolesByAccountId(Long accountId);
}
