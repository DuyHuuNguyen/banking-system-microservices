package com.banking_app.wallet_service.infrastructure.rest.controller;

import com.banking_app.wallet_service.api.facade.WalletFacade;
import com.banking_app.wallet_service.api.request.UpsertWalletRequest;
import com.banking_app.wallet_service.api.response.WalletDetailResponse;
import com.banking_app.wallet_service.api.response.WalletResponse;
import com.example.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
  public Mono<BaseResponse<List<WalletResponse>>> findAllPersonalWallets() {
    return this.walletFacade.findAllPersonalWallets();
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(tags = {"Wallets APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public Mono<BaseResponse<Void>> updatePersonalWallet(
      @PathVariable Long id, @RequestBody @Valid UpsertWalletRequest upsertWalletRequest) {
    upsertWalletRequest.withId(id);
    return this.walletFacade.updatePersonalWallet(upsertWalletRequest);
  }

  @GetMapping("/detail/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(tags = {"Wallets APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public Mono<BaseResponse<WalletDetailResponse>> findWalletDetailById(@PathVariable Long id) {
    return this.walletFacade.findWalletDetailById(id);
  }
}
