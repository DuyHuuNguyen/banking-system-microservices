package com.banking_app.user_service.infrastructure.util;

import com.banking_app.user_service.application.dto.WithinDateRangeDTO;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import lombok.Getter;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;

@Getter
public class PersonalInformationSpecification {
  private Query query;

  private PersonalInformationSpecification(Criteria criteria, Integer pageSize, Integer offset) {
    this.query = Query.query(criteria).limit(pageSize).offset(offset);
  }

  public static PersonalInformationSpecificationBuilder builder() {
    return new PersonalInformationSpecificationBuilderImpl();
  }

  public static class PersonalInformationSpecificationBuilderImpl
      implements PersonalInformationSpecificationBuilder {
    private Criteria criteria;
    private Integer pageSize;
    private Integer pageNumber;

    public PersonalInformationSpecificationBuilderImpl() {
      this.criteria = Criteria.empty();
    }

    @Override
    public PersonalInformationSpecification build() {
      Integer offset = this.pageNumber * this.pageSize;
      return new PersonalInformationSpecification(this.criteria, this.pageSize, offset);
    }

    @Override
    public PersonalInformationSpecificationBuilder createdAt(Long createdAt) {
      if (createdAt == null) return this;
      LocalDate date = Instant.ofEpochMilli(createdAt).atZone(ZoneId.systemDefault()).toLocalDate();
      long startOfDay = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
      long endOfDay =
          date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1;

      this.criteria = this.criteria.and("created_at").between(startOfDay, endOfDay);
      return this;
    }

    @Override
    public PersonalInformationSpecificationBuilder createdAtWithinRange(
        WithinDateRangeDTO dateRange) {
      if (dateRange == null) return this;
      this.criteria =
          this.criteria.and("created_at").between(dateRange.getFrom(), dateRange.getTo());
      return this;
    }

    @Override
    public PersonalInformationSpecificationBuilder pageSize(Integer pageSize) {
      if (pageSize == null) return this;
      this.pageSize = pageSize;
      return this;
    }

    @Override
    public PersonalInformationSpecificationBuilder sex(String sex) {
      if (sex == null) return this;
      this.criteria = this.criteria.and("sex").is(sex);
      return this;
    }

    @Override
    public PersonalInformationSpecificationBuilder pageNumber(Integer pageNumber) {
      if (pageNumber == null) return this;
      this.pageNumber = pageNumber;
      return this;
    }
  }
}
