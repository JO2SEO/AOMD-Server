package com.jo2seo.aomd.controller.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class FindOneByShareUrlResponse {
    Boolean readOnly;
    String title;
    Boolean sharing;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<String> blockIdList;
}
