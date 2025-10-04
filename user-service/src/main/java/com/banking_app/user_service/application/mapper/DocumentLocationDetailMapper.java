package com.banking_app.user_service.application.mapper;

import com.banking_app.user_service.application.dto.LocationDTO;
import com.banking_app.user_service.domain.entity.document_location_detail.DocumentLocationDetail;

public interface DocumentLocationDetailMapper {
  DocumentLocationDetail toDocumentLocationDetail(LocationDTO locationDTO);

  LocationDTO toDocumentLocationDetailDTO(DocumentLocationDetail documentLocationDetail);
}
