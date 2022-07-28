package com.jo2seo.aomd.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class FindOneByShareUrlResponse {
    String title;
    Boolean isShared;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<String> chainIdList;
}
