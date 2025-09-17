package com.banking_app.auth_service.api.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UpsertRoleAccountRequest {
  @NotNull private Long accountId;
  @NotNull private List<Long> roleIds;
}
