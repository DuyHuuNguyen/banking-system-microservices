package com.banking_app.auth_service.api.facade;

import com.banking_app.auth_service.api.request.UpsertRoleAccountRequest;
import com.example.base.BaseResponse;
import reactor.core.publisher.Mono;

public interface RoleFacade {
  Mono<BaseResponse<Void>> changeRoleAccount(UpsertRoleAccountRequest upsertRoleAccountRequest);
}
