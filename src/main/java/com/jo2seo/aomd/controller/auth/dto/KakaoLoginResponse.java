package com.jo2seo.aomd.controller.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoLoginResponse {
    private final String email;
    private final String password;
}
