package com.banking_app.user_service.infrastructure.rest.controller;

import com.banking_app.user_service.api.facade.PersonalInformationFacade;
import com.banking_app.user_service.api.request.ChangePersonalPhotoRequest;
import com.banking_app.user_service.api.request.PersonalInformationCriteria;
import com.banking_app.user_service.api.request.UpsertPersonalInformationRequest;
import com.banking_app.user_service.api.response.PersonalInformationDetailResponse;
import com.banking_app.user_service.api.response.PersonalInformationResponse;
import com.example.base.BaseResponse;
import com.example.base.PaginationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
  @Operation(tags = {"Personal-information APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_EMPLOYEE')||hasRole('ROLE_ADMIN')")
  public Mono<BaseResponse<Void>> updatePersonalInformation(
      @PathVariable("id") Long id,
      @RequestBody @Valid UpsertPersonalInformationRequest upsertPersonalInformationRequest) {
    upsertPersonalInformationRequest.withId(id);
    return this.personalInformationFacade.updateIdentifyDocument(upsertPersonalInformationRequest);
  }

  @GetMapping("/personal-information-profile")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Personal-information APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public Mono<BaseResponse<PersonalInformationDetailResponse>> findPersonalInformationProfile() {
    return this.personalInformationFacade.findPersonalInformationProfile();
  }

  @PatchMapping("/personal-photo")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Personal-information APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public Mono<BaseResponse<Void>> changePersonalPhoto(
      ChangePersonalPhotoRequest changePersonalPhotoRequest) {
    return this.personalInformationFacade.changePersonalPhoto(changePersonalPhotoRequest);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Personal-information APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_EMPLOYEE')")
  public Mono<BaseResponse<PaginationResponse<PersonalInformationResponse>>> findByFilter(
      @NotNull PersonalInformationCriteria personalInformationCriteria) {
    return this.personalInformationFacade.findByFilter(personalInformationCriteria);
  }

  @GetMapping("/detail/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Personal-information APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_EMPLOYEE')")
  public Mono<BaseResponse<PersonalInformationDetailResponse>> findDetailById(
      @PathVariable("id") Long id) {
    return this.personalInformationFacade.findDetailById(id);
  }
}
