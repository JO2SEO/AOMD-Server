package com.jo2seo.aomd.auth.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoLoginResponse {
    private final String email;
    private final String password;
}
