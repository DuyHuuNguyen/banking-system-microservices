package com.banking_app.user_service.api.facade;

import com.banking_app.user_service.api.request.CreateUserRequest;
import com.banking_app.user_service.api.request.UpdateUserRequest;
import com.banking_app.user_service.api.response.ProfileResponse;
import com.banking_app.user_service.api.response.UserDetailResponse;
import com.example.base.BaseResponse;
import reactor.core.publisher.Mono;

public interface UserFacade {
  Mono<BaseResponse<Void>> signUp(CreateUserRequest upsertUserRequest);

  Mono<BaseResponse<ProfileResponse>> getProfile();

  Mono<BaseResponse<UserDetailResponse>> findDetailById(Long id);

  Mono<BaseResponse<Void>> updateUser(UpdateUserRequest updateUserRequest);
}
