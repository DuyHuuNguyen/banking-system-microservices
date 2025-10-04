package com.banking_app.user_service.infrastructure.rest.controller;

import com.banking_app.user_service.api.facade.PersonalInformationFacade;
import com.banking_app.user_service.api.request.UpsertPersonalInformationRequest;
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
@RequestMapping("/api/v1/personal-information")
@RequiredArgsConstructor
public class PersonalInformationController {
  private final PersonalInformationFacade personalInformationFacade;

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Users APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_EMPLOYEE')||hasRole('ROLE_ADMIN')")
  public Mono<BaseResponse<Void>> updatePersonalInformation(
      @PathVariable("id") Long id,
      @RequestBody @Valid UpsertPersonalInformationRequest upsertPersonalInformationRequest) {
    upsertPersonalInformationRequest.withId(id);
    return this.personalInformationFacade.updateIdentifyDocument(upsertPersonalInformationRequest);
  }
}
