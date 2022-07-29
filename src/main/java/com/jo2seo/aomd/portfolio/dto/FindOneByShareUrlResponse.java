package com.jo2seo.aomd.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class FindOneByShareUrlResponse {
    String title;
    Boolean sharing;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<String> blockIdList;
}
