package com.banking_app.auth_service.api.facade;

import com.banking_app.auth_service.api.request.UpsertRoleAccountRequest;
import com.banking_app.auth_service.api.request.UpsertRoleRequest;
import com.banking_app.auth_service.api.response.RoleResponse;
import com.example.base.BaseCriteria;
import com.example.base.BaseResponse;
import com.example.base.PaginationResponse;
import reactor.core.publisher.Mono;

public interface RoleFacade {
  Mono<BaseResponse<Void>> changeRoleAccount(UpsertRoleAccountRequest upsertRoleAccountRequest);

  Mono<BaseResponse<Void>> createRole(UpsertRoleRequest upsertRoleRequest);

  Mono<BaseResponse<PaginationResponse<RoleResponse>>> findAll(BaseCriteria baseCriteria);
}
