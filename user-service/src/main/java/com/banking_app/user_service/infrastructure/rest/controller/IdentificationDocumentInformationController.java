package com.banking_app.user_service.infrastructure.rest.controller;

import com.banking_app.user_service.api.facade.IdentifyDocumentInformationFacade;
import com.banking_app.user_service.api.request.UpsertIdentificationDocumentInformationRequest;
import com.banking_app.user_service.api.response.IdentificationDocumentDetailResponse;
import com.example.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/identify-documents")
@RequiredArgsConstructor
public class IdentificationDocumentInformationController {
  private final IdentifyDocumentInformationFacade identifyDocumentInformationFacade;

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Identify-documents APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_EMPLOYEE')||hasRole('ROLE_ADMIN')")
  public Mono<BaseResponse<Void>> updateIdentifyDocument(
      @PathVariable Long id,
      @RequestBody @Valid
          UpsertIdentificationDocumentInformationRequest
              upsertIdentificationDocumentInformationRequest) {
    upsertIdentificationDocumentInformationRequest.withId(id);
    return this.identifyDocumentInformationFacade.updateIdentifyDocument(
        upsertIdentificationDocumentInformationRequest);
  }

  @GetMapping("/detail/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Identify-documents APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_EMPLOYEE')||hasRole('ROLE_ADMIN')")
  public Mono<BaseResponse<IdentificationDocumentDetailResponse>> findDetailById(
      @PathVariable Long id) {
    return this.identifyDocumentInformationFacade.findDetailById(id);
  }
}
