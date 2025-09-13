package com.banking_app.auth_service.infrastructure.facade;

import com.banking_app.auth_service.api.facade.AuthFacade;
import com.banking_app.auth_service.api.request.LoginRequest;
import com.banking_app.auth_service.api.request.RefreshTokenRequest;
import com.banking_app.auth_service.api.response.LoginResponse;
import com.banking_app.auth_service.api.response.RefreshTokenResponse;
import com.banking_app.auth_service.application.service.AccountService;
import com.banking_app.auth_service.application.service.CacheService;
import com.banking_app.auth_service.application.service.JwtService;
import com.banking_app.auth_service.application.service.RoleService;
import com.example.base.BaseResponse;
import com.example.enums.TokenTemplate;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthFacadeImpl implements AuthFacade {
  private final AccountService accountService;
  private final RoleService roleService;
  private final JwtService jwtService;
  private final CacheService cacheService;
  private final ReactiveAuthenticationManager reactiveAuthenticationManager;

  @Override
  public Mono<BaseResponse<LoginResponse>> login(LoginRequest loginRequest) {
    return this.reactiveAuthenticationManager
        .authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getPersonalIdentificationNumber(), loginRequest.getPassword()))
        .flatMap(
            authentication ->
                Mono.fromCallable(
                    () -> {
                      log.info(
                          " request body: {} , authentication {}",
                          loginRequest,
                          authentication.isAuthenticated());
                      var accessToken =
                          jwtService.generateAccessToken(
                              loginRequest.getPersonalIdentificationNumber());
                      var refreshToken =
                          jwtService.generateRefreshToken(
                              loginRequest.getPersonalIdentificationNumber());

                      var accessTokenCacheKey =
                          String.format(
                              TokenTemplate.ACCESS_TOKEN.getContent(),
                              loginRequest.getPersonalIdentificationNumber());
                      var refreshTokenCacheKey =
                          String.format(
                              TokenTemplate.REFRESH_TOKEN.getContent(),
                              loginRequest.getPersonalIdentificationNumber());

                      cacheService.store(accessTokenCacheKey, accessToken, 1, TimeUnit.HOURS);
                      cacheService.store(refreshTokenCacheKey, refreshToken, 14, TimeUnit.DAYS);

                      return BaseResponse.build(
                          LoginResponse.builder()
                              .accessToken(accessToken)
                              .refreshToken(refreshToken)
                              .build(),
                          true);
                    }));
  }

  @Override
  public Mono<BaseResponse<RefreshTokenResponse>> refreshToken(
      RefreshTokenRequest refreshTokenRequest) {
    var personalIdentifyNumber =
        this.jwtService.getPersonalIdentificationNumberFromJwtToken(
            refreshTokenRequest.getRefreshToken());
    log.info("Refresh token | personalIdentifyNumber: {}", personalIdentifyNumber);

    var refreshTokenCacheKey =
        String.format(TokenTemplate.ACCESS_TOKEN.getContent(), personalIdentifyNumber);
    var isMissingTokenInCache = !this.cacheService.hasKey(refreshTokenCacheKey);
    if (isMissingTokenInCache) {
      throw new RuntimeException("messing token");
    }

    var accessToken = jwtService.generateAccessToken(personalIdentifyNumber);
    var accessTokenCacheKey =
        String.format(TokenTemplate.ACCESS_TOKEN.getContent(), personalIdentifyNumber);
    cacheService.store(accessTokenCacheKey, accessToken, 1, TimeUnit.HOURS);

    return Mono.just(
        BaseResponse.build(RefreshTokenResponse.builder().accessToken(accessToken).build(), true));
  }
}
