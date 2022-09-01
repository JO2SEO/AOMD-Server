package com.jo2seo.aomd.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PatchPortfolioSharingRequest {
    @NotNull
    private boolean sharing;
}
