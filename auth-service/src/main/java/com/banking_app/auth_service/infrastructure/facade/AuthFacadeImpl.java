package com.banking_app.auth_service.infrastructure.facade;

import com.banking_app.auth_service.api.facade.AuthFacade;
import com.banking_app.auth_service.api.request.CreateOtpRequest;
import com.banking_app.auth_service.api.request.LoginRequest;
import com.banking_app.auth_service.api.request.RefreshTokenRequest;
import com.banking_app.auth_service.api.request.UpsertAccountRequest;
import com.banking_app.auth_service.api.response.LoginResponse;
import com.banking_app.auth_service.api.response.RefreshTokenResponse;
import com.banking_app.auth_service.application.service.AccountService;
import com.banking_app.auth_service.application.service.CacheService;
import com.banking_app.auth_service.application.service.JwtService;
import com.banking_app.auth_service.infrastructure.security.SecurityUserDetails;
import com.example.base.BaseResponse;
import com.example.dto.AccountDTO;
import com.example.enums.ErrorCode;
import com.example.enums.TokenTemplate;
import com.example.exception.EntityNotFoundException;
import com.example.exception.OtpException;
import com.example.exception.PermissionDeniedException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthFacadeImpl implements AuthFacade {
  private final AccountService accountService;
  private final JwtService jwtService;
  private final CacheService cacheService;
  private final PasswordEncoder passwordEncoder;
  private final ReactiveAuthenticationManager reactiveAuthenticationManager;

  private final Boolean IS_FIRST_LOGIN = false;
  private final Boolean IS_ONE_DEVICE = false;
  private final Logger log = LogManager.getLogger(AuthFacade.class);

  @Override
  public Mono<BaseResponse<LoginResponse>> login(LoginRequest loginRequest) {
    return this.reactiveAuthenticationManager
        .authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getPersonalIdentificationNumber(), loginRequest.getPassword()))
        .flatMap(
            authentication -> {
              var principal = (SecurityUserDetails) authentication.getPrincipal();
              if (!principal.isValid())
                return Mono.error(new PermissionDeniedException(ErrorCode.LOGGED_IN));
              log.info(
                  " request body: {} , authentication {}",
                  loginRequest,
                  authentication.isAuthenticated());

              var accessToken =
                  jwtService.generateAccessToken(loginRequest.getPersonalIdentificationNumber());
              var refreshToken =
                  jwtService.generateRefreshToken(loginRequest.getPersonalIdentificationNumber());

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
              log.info("complete cache token");
              this.accountService.updateFirstLoginAndOneDeviceByPersonalId(
                  loginRequest.getPersonalIdentificationNumber(),
                  this.IS_FIRST_LOGIN,
                  this.IS_ONE_DEVICE);
              return Mono.just(
                  BaseResponse.build(
                      LoginResponse.builder()
                          .accessToken(accessToken)
                          .refreshToken(refreshToken)
                          .build(),
                      true));
            });
  }

  @Override
  public Mono<BaseResponse<RefreshTokenResponse>> refreshToken(
      RefreshTokenRequest refreshTokenRequest) {
    var personalIdentifyNumber =
        this.jwtService.getPersonalIdentificationNumberFromJwtToken(
            refreshTokenRequest.getRefreshToken());
    log.info("Refresh token | personalIdentifyNumber: {}", personalIdentifyNumber);

    var refreshTokenCacheKey =
        String.format(TokenTemplate.REFRESH_TOKEN.getContent(), personalIdentifyNumber);
    var isMissingTokenInCache = !this.cacheService.hasKey(refreshTokenCacheKey);
    if (isMissingTokenInCache) {
      throw new EntityNotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }

    var accessToken = jwtService.generateAccessToken(personalIdentifyNumber);
    var accessTokenCacheKey =
        String.format(TokenTemplate.ACCESS_TOKEN.getContent(), personalIdentifyNumber);
    cacheService.store(accessTokenCacheKey, accessToken, 1, TimeUnit.HOURS);
    log.info("store into cache");
    return Mono.just(
        BaseResponse.build(RefreshTokenResponse.builder().accessToken(accessToken).build(), true));
  }

  @Override
  public Mono<BaseResponse<Void>> logout() {

    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(authentication -> (SecurityUserDetails) authentication.getPrincipal())
        .flatMap(
            principal -> {
              var personalIdentificationNumber = principal.getPersonalIdentificationNumber();
              var refreshTokenCacheKey =
                  String.format(
                      TokenTemplate.ACCESS_TOKEN.getContent(), personalIdentificationNumber);
              var accessTokenKey =
                  String.format(
                      TokenTemplate.ACCESS_TOKEN.getContent(), personalIdentificationNumber);

              this.cacheService.delete(accessTokenKey);
              this.cacheService.delete(refreshTokenCacheKey);

              return Mono.just(BaseResponse.ok());
            });
  }

  @Override
  public Mono<BaseResponse<Void>> changeInfoAccount(UpsertAccountRequest upsertAccountRequest) {
    return this.accountService
        .findById(upsertAccountRequest.getId())
        .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND)))
        .flatMap(
            account -> {
              var passwordEndCoded =
                  this.passwordEncoder.encode(upsertAccountRequest.getPassword());
              var accountDTO =
                  AccountDTO.builder()
                      .email(upsertAccountRequest.getEmail())
                      .passwordEncoded(passwordEndCoded)
                      .phone(upsertAccountRequest.getPhone())
                      .personalIdentificationNumber(
                          upsertAccountRequest.getPersonalIdentificationNumber())
                      .otp(upsertAccountRequest.getOtp())
                      .userId(upsertAccountRequest.getUserId())
                      .isOneDevice(upsertAccountRequest.getIsOneDevice())
                      .isFirstLogin(upsertAccountRequest.getIsFirstLogin())
                      .build();
              account.changeInfo(accountDTO);
              this.accountService.save(account);
              return Mono.just(BaseResponse.ok());
            });
  }

  @Override
  public Mono<BaseResponse<Void>> accessLogin(Long id) {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(authentication -> (SecurityUserDetails) authentication.getPrincipal())
        .flatMap(
            securityUserDetails -> {
              this.accountService.updateFirstLoginAndOneDeviceById(
                  id, !this.IS_FIRST_LOGIN, !this.IS_ONE_DEVICE);
              log.info(
                  "An employee with id = {} allowed account with id ={} to login.",
                  securityUserDetails.getAccountId(),
                  id);
              return Mono.just(BaseResponse.ok());
            });
  }

  @Override
  public Mono<BaseResponse<Void>> createOtp(CreateOtpRequest createOtpRequest) {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(authentication -> (SecurityUserDetails) authentication.getPrincipal())
        .flatMap(
            securityUserDetails -> {
              var isValidateOtp =
                  createOtpRequest.getOpt().equals(createOtpRequest.getOtpConfirm());
              if (!isValidateOtp) Mono.error(new OtpException(ErrorCode.OTP_NONE_MATCH));

              return this.accountService
                  .findById(securityUserDetails.getAccountId())
                  .switchIfEmpty(
                      Mono.error(new EntityNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND)))
                  .flatMap(
                      account -> {
                        account.updateOtp(createOtpRequest.getOpt());
                        this.accountService.save(account);
                        return Mono.just(BaseResponse.ok());
                      });
            });
  }
}
