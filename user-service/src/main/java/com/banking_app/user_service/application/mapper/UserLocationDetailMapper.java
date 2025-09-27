package com.banking_app.user_service.application.mapper;

import com.banking_app.user_service.application.dto.LocationDTO;
import com.banking_app.user_service.domain.entity.user_location_detail.UserLocationDetail;

public interface UserLocationDetailMapper {
  UserLocationDetail toUserLocationDetail(LocationDTO locationDTO);

  LocationDTO toUserLocationDetailDTO(UserLocationDetail userLocationDetail);
}
