package com.banking_app.transaction_service.domain.entity.common;

import java.time.Instant;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@ToString
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

  @Id
  @Column("id")
  private Long id;

  @Column("version")
  @Builder.Default
  private Long version = 0L;

  @Column("is_active")
  @Builder.Default
  private boolean active = true;

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
