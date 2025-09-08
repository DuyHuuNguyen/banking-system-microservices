package com.banking_app.user_service.domain.entity.common;

import java.time.Instant;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@ToString
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

  @Id private Long id;

  @Column("version")
  @Builder.Default
  private Long version = 0L;

  @Builder.Default boolean isActive = true;

  @Column("created_at")
  @Builder.Default
  private Long createdAt = Instant.now().toEpochMilli();;

  @Column("updated_at")
  @Builder.Default
  private Long updatedAt = Instant.now().toEpochMilli();;

  public void reUpdate() {
    this.updatedAt = Instant.now().toEpochMilli();
  }
}
