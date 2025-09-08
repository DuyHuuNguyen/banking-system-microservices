package com.banking_app.user_service.domain.repository;

import com.banking_app.user_service.domain.entity.user.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {}
