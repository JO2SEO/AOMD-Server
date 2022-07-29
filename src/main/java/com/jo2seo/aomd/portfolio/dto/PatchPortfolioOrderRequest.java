package com.jo2seo.aomd.portfolio.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class PatchPortfolioOrderRequest {
    @NotNull
    private List<String> blockIdList;
}
