package com.banking_app.user_service.infrastructure.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;

@Getter
@NoArgsConstructor
public class IdentificationDocumentInformationSpecification {
  private Query query;

  private IdentificationDocumentInformationSpecification(
      Criteria criteria, Integer pageSize, Integer offset) {
    this.query = Query.query(criteria).limit(pageSize).offset(offset);
  }

  public static class IdentificationDocumentInformationSpecificationBuilderImpl
      implements IdentificationDocumentInformationSpecificationBuilder {
    private Criteria criteria;
    private Integer pageSize;
    private Integer pageNumber;

    public IdentificationDocumentInformationSpecificationBuilderImpl() {
      this.criteria = Criteria.empty();
    }

    @Override
    public IdentificationDocumentInformationSpecificationBuilder createdAt(Long createdAt) {
      if (createdAt == null) return this;
      LocalDate date = Instant.ofEpochMilli(createdAt).atZone(ZoneId.systemDefault()).toLocalDate();
      long startOfDay = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
      long endOfDay =
          date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1;

      this.criteria = this.criteria.and("created_at").between(startOfDay, endOfDay);
      return this;
    }

    @Override
    public IdentificationDocumentInformationSpecificationBuilder issuedAt(Long issuedAt) {
      if (issuedAt == null) return this;
      LocalDate date = Instant.ofEpochMilli(issuedAt).atZone(ZoneId.systemDefault()).toLocalDate();
      long startOfDay = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
      long endOfDay =
          date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1;

      this.criteria = this.criteria.and("issued_at").between(startOfDay, endOfDay);
      return this;
    }

    @Override
    public IdentificationDocumentInformationSpecificationBuilder builder() {
      return new IdentificationDocumentInformationSpecificationBuilderImpl();
    }

    @Override
    public IdentificationDocumentInformationSpecificationBuilder pageNumber(Integer pageNumber) {
      if (pageNumber == null) return this;
      this.pageNumber = pageNumber;
      return this;
    }

    @Override
    public IdentificationDocumentInformationSpecificationBuilder pageSize(Integer pageSize) {
      if (pageSize == null) return this;
      this.pageSize = pageSize;
      return this;
    }

    @Override
    public IdentificationDocumentInformationSpecification build() {
      Integer offset = this.pageNumber * this.pageSize;
      return new IdentificationDocumentInformationSpecification(
          this.criteria, this.pageSize, offset);
    }
  }
}
