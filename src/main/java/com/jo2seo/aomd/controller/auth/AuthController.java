package com.jo2seo.aomd.controller.auth;

import com.jo2seo.aomd.controller.auth.dto.BasicLoginRequest;
import com.jo2seo.aomd.controller.auth.dto.KakaoLoginRequest;
import com.jo2seo.aomd.controller.auth.dto.LoginRequest;
import com.jo2seo.aomd.controller.auth.dto.LoginResponse;
import com.jo2seo.aomd.security.TokenProvider;
import com.jo2seo.aomd.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

import static com.jo2seo.aomd.security.JwtFilter.AUTHORIZATION_HEADER;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @Operation(
            summary = "일반 로그인",
            description = "이메일과 패스워드를 입력받아서 로그인 시도", tags = "auth")
    @PostMapping("/auth/login")
    public ResponseEntity login(
            @Valid @RequestBody BasicLoginRequest basicLoginRequest,
            HttpServletResponse response
    ) {
        String jwt = tokenProvider.createToken(basicLoginRequest, 5 * 60);
        String rjwt = tokenProvider.createToken(basicLoginRequest, 60 * 60);

        Cookie cookie = new Cookie("refreshToken", rjwt);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return new ResponseEntity(new LoginResponse(jwt), OK);
    }

    @GetMapping("/auth/test")
    public void kakaoLoginTest (
            @RequestParam String code
    ) {
        System.out.println("code = " + code);
    }

    /* TODO: request에서 callbackUrl제거 */
    @PostMapping("/auth/kakao")
    public ResponseEntity kakaoLogin(
            HttpServletRequest request,
            @Valid @RequestBody KakaoLoginRequest kakaoLoginRequest
    ) throws IOException {
        if (kakaoLoginRequest.getCode() == null) return new ResponseEntity<>(BAD_REQUEST);
        String origin = request.getHeader("origin");
        if (origin == null) return new ResponseEntity<>(BAD_REQUEST);

        String callbackUrl = origin + "/AOMD-Client/oauth";
        log.debug(callbackUrl);

        LoginRequest kakaoLoginResponse = authService.kakaoLogin(kakaoLoginRequest.getCode(), kakaoLoginRequest.getCallbackUrl());
        String jwt = tokenProvider.createToken(kakaoLoginResponse, 5 * 60);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity(httpHeaders, OK);
    }
}
