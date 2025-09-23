package com.banking_app.user_service.api.facade;

import com.banking_app.user_service.api.request.UpsertUserRequest;
import com.example.base.BaseResponse;
import reactor.core.publisher.Mono;

public interface UserFacade {
  Mono<BaseResponse<Void>> signUp(UpsertUserRequest upsertUserRequest);
}
