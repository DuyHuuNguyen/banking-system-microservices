package com.banking_app.wallet_service.infrastructure.rest.controller;

import com.banking_app.wallet_service.api.facade.WalletFacade;
import com.banking_app.wallet_service.api.request.UpsertWalletRequest;
import com.banking_app.wallet_service.api.request.WalletResponse;
import com.example.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {
  private final WalletFacade walletFacade;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(tags = {"Wallets APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public Mono<BaseResponse<Void>> create(
      @RequestBody @Valid UpsertWalletRequest upsertWalletRequest) {
    return this.walletFacade.createWallet(upsertWalletRequest);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(tags = {"Wallets APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public Mono<BaseResponse<List<WalletResponse>>> findAllPersonalWallets(){
      return this.walletFacade.findAllPersonalWallets();
  }
}
