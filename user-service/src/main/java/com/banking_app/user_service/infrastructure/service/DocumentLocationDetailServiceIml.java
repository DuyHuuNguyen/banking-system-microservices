package com.banking_app.user_service.infrastructure.service;

import com.banking_app.user_service.application.service.DocumentLocationDetailService;
import com.banking_app.user_service.domain.entity.document_location_detail.DocumentLocationDetail;
import com.banking_app.user_service.domain.repository.DocumentLocationDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DocumentLocationDetailServiceIml implements DocumentLocationDetailService {
  private final DocumentLocationDetailRepository documentLocationDetailRepository;

  @Override
  public Mono<DocumentLocationDetail> save(DocumentLocationDetail documentLocationDetail) {
    return this.documentLocationDetailRepository.save(documentLocationDetail);
  }

  //  @PostConstruct
  //  void run(){
  //    var documentLocationDetail = DocumentLocationDetail.builder()
  //
  // .country("upsertUserRequest.getLocationOfIdentityDocumentInformationDTO().getCountry()")
  //
  // .province("upsertUserRequest.getLocationOfIdentityDocumentInformationDTO().getProvince()")
  //            .ward("upsertUserRequest.getLocationOfIdentityDocumentInformationDTO().getWard()")
  //
  // .street("upsertUserRequest.getLocationOfIdentityDocumentInformationDTO().getStreet()")
  //
  // .homesNumber("upsertUserRequest.getLocationOfIdentityDocumentInformationDTO().getHomesNumber()")
  //            .build();
  //    this.save(documentLocationDetail).doOnNext(System.out::println).subscribe();
  //
  //  }
}
