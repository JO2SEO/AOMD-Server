package com.jo2seo.aomd.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PostResumeRequest {
    @NotNull
    private Long portfolioId;
    @NotNull
    private String question;
    @NotNull
    private String content;
}
