package com.banking_app.auth_service.infrastructure.until;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;

@Log4j2
@Getter
public class AccountSpecification {
  private Query query;

  private AccountSpecification() {}

  private AccountSpecification(Criteria criteria, Integer pageSize, Integer offset) {
    this.query = Query.query(criteria).limit(pageSize).offset(offset);
  }

  private AccountSpecification(Criteria criteria) {
    this.query = Query.query(criteria);
  }

  public static AccountSpecificationBuilderImpl builder() {
    return new AccountSpecificationBuilderImpl();
  }

  public static class AccountSpecificationBuilderImpl implements AccountSpecificationBuilder {
    private Criteria criteria;
    private Integer pageSize;
    private Integer pageNumber;

    public AccountSpecificationBuilderImpl() {
      this.criteria = Criteria.empty();
    }

    @Override
    public AccountSpecification build() {
      Integer offset = this.pageNumber * this.pageSize;
      log.info("offset = {}", offset);
      return new AccountSpecification(this.criteria, this.pageSize, offset);
    }

    @Override
    public AccountSpecificationBuilder builder() {
      return new AccountSpecificationBuilderImpl();
    }

    @Override
    public AccountSpecificationBuilder phone(String phone) {
      if (phone == null) return this;
      this.criteria = this.criteria.and("phone").like(phone);
      return this;
    }

    @Override
    public AccountSpecificationBuilder email(String email) {
      if (email == null) return this;
      this.criteria = this.criteria.and("email").like("%" + email + "%");
      return this;
    }

    @Override
    public AccountSpecificationBuilder personalId(String personalId) {
      if (personalId == null) return this;
      this.criteria = this.criteria.and("personal_identification_number").like(personalId);
      return this;
    }

    @Override
    public AccountSpecificationBuilder pageSize(Integer pageSize) {
      if (pageSize == null) return this;
      this.pageSize = pageSize;
      return this;
    }

    @Override
    public AccountSpecificationBuilder pageNumber(Integer pageNumber) {
      if (pageNumber == null) return this;
      this.pageNumber = pageNumber;
      return this;
    }
  }

  public static void main(String[] args) {
    AccountSpecification accountSpecification =
        AccountSpecification.builder().email("hehe").email("hkjsdhf").build();
    accountSpecification.getQuery();
  }
}
