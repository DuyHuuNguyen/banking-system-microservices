package com.example.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class UpdatingAccountMessage {
  private Long userId;
  private String personalId;
  private String email;

  public void addUserId(Long userId) {
    this.userId = userId;
  }

  public void addEmail(String email) {
    this.email = email;
  }
}
