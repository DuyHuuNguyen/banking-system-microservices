package com.banking_app.auth_service.domain.repository;

import com.banking_app.auth_service.domain.entity.role.Role;
import com.example.enums.RoleEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    Mono<Role> findByRoleName(RoleEnum roleName);

    @Query("SELECT * FROM roles LIMIT :limits OFFSET :offsets")
    Flux<Role> findAll(@Param("limits") int limit, @Param("offsets") int offset);

    Flux<Role> findAllBy(Pageable pageable);
}
