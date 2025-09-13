package com.banking_app.auth_service.api.facade;

import com.banking_app.auth_service.api.request.LoginRequest;
import com.banking_app.auth_service.api.request.RefreshTokenRequest;
import com.banking_app.auth_service.api.response.LoginResponse;
import com.banking_app.auth_service.api.response.RefreshTokenResponse;
import com.example.base.BaseResponse;
import reactor.core.publisher.Mono;

public interface AuthFacade {
  Mono<BaseResponse<LoginResponse>> login(LoginRequest loginRequest);

  Mono<BaseResponse<RefreshTokenResponse>> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
