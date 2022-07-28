package com.jo2seo.aomd.portfolio.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PatchPortfolioSharedRequest {
    @NotNull
    private boolean shared;
}
