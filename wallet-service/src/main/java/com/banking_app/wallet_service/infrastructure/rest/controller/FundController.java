package com.banking_app.wallet_service.infrastructure.rest.controller;

import com.banking_app.wallet_service.api.facade.FundFacade;
import com.banking_app.wallet_service.api.request.UpsertFundRequest;
import com.example.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/funds")
@RequiredArgsConstructor
public class FundController {
  private final FundFacade fundFacade;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(tags = {"Funds APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public Mono<BaseResponse<Void>> createFund(
      @RequestBody @Valid UpsertFundRequest upsertFundRequest) {
    return this.fundFacade.createFund(upsertFundRequest);
  }
}
