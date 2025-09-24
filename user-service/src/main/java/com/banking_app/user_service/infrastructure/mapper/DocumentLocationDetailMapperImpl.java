package com.banking_app.user_service.infrastructure.mapper;

import com.banking_app.user_service.application.dto.LocationDTO;
import com.banking_app.user_service.application.mapper.DocumentLocationDetailMapper;
import com.banking_app.user_service.domain.entity.document_location_detail.DocumentLocationDetail;
import org.springframework.stereotype.Service;

@Service
public class DocumentLocationDetailMapperImpl implements DocumentLocationDetailMapper {
  @Override
  public DocumentLocationDetail toDocumentLocationDetail(LocationDTO locationDTO) {
    return DocumentLocationDetail.builder()
        .country(locationDTO.getCountry())
        .province(locationDTO.getProvince())
        .ward(locationDTO.getWard())
        .street(locationDTO.getStreet())
        .district(locationDTO.getDistrict())
        .homesNumber(locationDTO.getHomesNumber())
        .build();
  }

  @Override
  public LocationDTO toDocumentLocationDetailDTO(DocumentLocationDetail documentLocationDetail) {
    return LocationDTO.builder()
        .country(documentLocationDetail.getCountry())
        .province(documentLocationDetail.getProvince())
        .ward(documentLocationDetail.getWard())
        .street(documentLocationDetail.getStreet())
        .district(documentLocationDetail.getDistrict())
        .homesNumber(documentLocationDetail.getHomesNumber())
        .build();
  }
}
