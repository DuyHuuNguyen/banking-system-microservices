package com.banking_app.user_service.infrastructure.mapper;

import com.banking_app.user_service.application.dto.LocationDTO;
import com.banking_app.user_service.application.mapper.UserLocationDetailMapper;
import com.banking_app.user_service.domain.entity.user_location_detail.UserLocationDetail;
import org.springframework.stereotype.Service;

@Service
public class UserLocationDetailImpl implements UserLocationDetailMapper {
  @Override
  public UserLocationDetail toUserLocationDetail(LocationDTO locationDTO) {
    return UserLocationDetail.builder()
        .country(locationDTO.getCountry())
        .province(locationDTO.getProvince())
        .ward(locationDTO.getWard())
        .street(locationDTO.getStreet())
        .district(locationDTO.getDistrict())
        .homesNumber(locationDTO.getHomesNumber())
        .build();
  }
}
