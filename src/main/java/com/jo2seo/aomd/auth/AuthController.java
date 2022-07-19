package com.jo2seo.aomd.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jo2seo.aomd.BaseResponse;
import com.jo2seo.aomd.BaseResponseStatus;
import com.jo2seo.aomd.auth.dto.request.LoginRequest;
import com.jo2seo.aomd.auth.dto.response.KakaoLoginResponse;
import com.jo2seo.aomd.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<BaseResponse> login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        String jwt = authService.genJwt(loginRequest.getEmail(), loginRequest.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity(new BaseResponse(jwt), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/auth/kakao")
    public ResponseEntity<BaseResponse> kakaoLogin(
        @Nullable @PathParam("code") String code
    ) throws JsonProcessingException {
        if (code == null) return new ResponseEntity(new BaseResponse(BaseResponseStatus.DATABASE_ERROR), HttpStatus.BAD_REQUEST);

        KakaoLoginResponse kakaoLoginResponse = authService.kakaoLogin(code, "http://aomd.kro.kr:8080/api/v1/auth/kakao");
        String jwt = authService.genJwt(kakaoLoginResponse.getEmail(), kakaoLoginResponse.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity(new BaseResponse(jwt), httpHeaders, HttpStatus.OK);
    }
}
