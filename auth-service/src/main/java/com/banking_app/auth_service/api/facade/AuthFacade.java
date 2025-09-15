package com.banking_app.auth_service.api.facade;

import com.banking_app.auth_service.api.request.*;
import com.banking_app.auth_service.api.response.ForgotPasswordResponse;
import com.banking_app.auth_service.api.response.LoginResponse;
import com.banking_app.auth_service.api.response.RefreshTokenResponse;
import com.example.base.AccountResponse;
import com.example.base.BaseResponse;
import reactor.core.publisher.Mono;

public interface AuthFacade {
  Mono<BaseResponse<LoginResponse>> login(LoginRequest loginRequest);

  Mono<BaseResponse<RefreshTokenResponse>> refreshToken(RefreshTokenRequest refreshTokenRequest);

  Mono<BaseResponse<Void>> logout();

  Mono<BaseResponse<Void>> changeInfoAccount(UpsertAccountRequest upsertAccountRequest);

  Mono<BaseResponse<Void>> accessLogin(Long id);

  Mono<BaseResponse<Void>> createOtp(CreateOtpRequest createOtpRequest);

  Mono<BaseResponse<Boolean>> isVerifyOtp(VerifyOptRequest verifyOptRequest);

  Mono<AccountResponse> findById(Long id);

  Mono<BaseResponse<ForgotPasswordResponse>> forgotPassword(
      ForgotPasswordRequest forgotPasswordRequest);

  Mono<BaseResponse<Void>> resetPassword(ResetPasswordRequest resetPasswordRequest);
}
