package com.jo2seo.aomd.dto.request;

import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class KakaoLoginRequest {
    @Nullable
    private String code;
    @Nullable
    private String callbackUrl;
}
