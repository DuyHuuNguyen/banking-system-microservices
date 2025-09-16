package com.example.base;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PaginationResponse<T> {
  private List<T> data;
  private Integer currentPage;
  private Integer pageSize;
}
