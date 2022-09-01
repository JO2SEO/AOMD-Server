package com.jo2seo.aomd.controller.auth;

import com.jo2seo.aomd.BaseResponse;
import com.jo2seo.aomd.BaseResponseStatus;
import com.jo2seo.aomd.service.auth.AuthService;
import com.jo2seo.aomd.dto.request.KakaoLoginRequest;
import com.jo2seo.aomd.dto.request.LoginRequest;
import com.jo2seo.aomd.dto.response.KakaoLoginResponse;
import com.jo2seo.aomd.security.JwtFilter;
import com.jo2seo.aomd.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
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

    /* TODO: request에서 callbackUrl제거 */
    @PostMapping("/auth/kakao")
    public ResponseEntity<BaseResponse> kakaoLogin(
            HttpServletRequest request,
            @Valid @RequestBody KakaoLoginRequest kakaoLoginRequest
    ) throws IOException {
        if (kakaoLoginRequest.getCode() == null) return new ResponseEntity(new BaseResponse(BaseResponseStatus.DATABASE_ERROR), HttpStatus.BAD_REQUEST);
        String origin = request.getHeader("origin");
        if (origin == null) return new ResponseEntity(new BaseResponse(BaseResponseStatus.DATABASE_ERROR), HttpStatus.BAD_REQUEST);

        String callbackUrl = origin + "/AOMD-Client/oauth";
        log.debug(callbackUrl);

        KakaoLoginResponse kakaoLoginResponse = authService.kakaoLogin(kakaoLoginRequest.getCode(), kakaoLoginRequest.getCallbackUrl());
        String jwt = tokenProvider.createToken(kakaoLoginResponse.getEmail(), kakaoLoginResponse.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity(new BaseResponse(jwt), httpHeaders, HttpStatus.OK);
    }
}
