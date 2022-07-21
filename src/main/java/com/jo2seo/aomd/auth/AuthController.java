package com.jo2seo.aomd.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jo2seo.aomd.BaseResponse;
import com.jo2seo.aomd.BaseResponseStatus;
import com.jo2seo.aomd.auth.dto.request.KakaoLoginRequest;
import com.jo2seo.aomd.auth.dto.request.LoginRequest;
import com.jo2seo.aomd.auth.dto.response.KakaoLoginResponse;
import com.jo2seo.aomd.security.JwtFilter;
import com.jo2seo.aomd.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @PostMapping("/auth/login")
    public ResponseEntity<BaseResponse> login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        String jwt = tokenProvider.createToken(loginRequest.getEmail(), loginRequest.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity(new BaseResponse(jwt), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/auth/test")
    public void kakaoLoginTest (
            @RequestParam String code
    ) {
        System.out.println("code = " + code);
    }

    @PostMapping("/auth/kakao")
    public ResponseEntity<BaseResponse> kakaoLogin(
            @Valid @RequestBody KakaoLoginRequest kakaoLoginRequest
    ) throws JsonProcessingException {
        if (kakaoLoginRequest.getCode() == null) return new ResponseEntity(new BaseResponse(BaseResponseStatus.DATABASE_ERROR), HttpStatus.BAD_REQUEST);

        KakaoLoginResponse kakaoLoginResponse = authService.kakaoLogin(kakaoLoginRequest.getCode(), kakaoLoginRequest.getCallbackUrl());
        String jwt = tokenProvider.createToken(kakaoLoginResponse.getEmail(), kakaoLoginResponse.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity(new BaseResponse(jwt), httpHeaders, HttpStatus.OK);
    }
}
