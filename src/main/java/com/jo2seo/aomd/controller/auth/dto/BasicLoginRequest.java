package com.jo2seo.aomd.controller.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BasicLoginRequest implements LoginRequest {
    private String email;
    private String password;
}
