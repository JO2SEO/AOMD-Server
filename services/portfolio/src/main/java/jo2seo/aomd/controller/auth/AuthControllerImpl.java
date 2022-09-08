package jo2seo.aomd.controller.auth;

import jo2seo.aomd.api.auth.AuthController;
import jo2seo.aomd.api.auth.dto.BasicLoginRequest;
import jo2seo.aomd.api.auth.dto.KakaoLoginRequest;
import jo2seo.aomd.api.auth.dto.LoginRequest;
import jo2seo.aomd.api.auth.dto.LoginResponse;
import jo2seo.aomd.security.TokenProvider;
import jo2seo.aomd.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static jo2seo.aomd.security.JwtFilter.AUTHORIZATION_HEADER;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    @Qualifier("kakaoAuthService")
    private final AuthService kakaoAuthService;
    private final TokenProvider tokenProvider;

    @Override
    public ResponseEntity login(BasicLoginRequest basicLoginRequest, HttpServletResponse response) {
        String jwt = tokenProvider.createToken(basicLoginRequest, 5 * 60);
        String rjwt = tokenProvider.createToken(basicLoginRequest, 60 * 60);

        Cookie cookie = new Cookie("refreshToken", rjwt);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return new ResponseEntity(new LoginResponse(jwt), OK);
    }

    /* TODO: request에서 callbackUrl제거 */
    @Override
    public ResponseEntity kakaoLogin(
            HttpServletRequest request,
            KakaoLoginRequest kakaoLoginRequest
    ) throws IOException {
        if (kakaoLoginRequest.getCode() == null) return new ResponseEntity<>(BAD_REQUEST);
        String origin = request.getHeader("origin");
        if (origin == null) return new ResponseEntity<>(BAD_REQUEST);

        String callbackUrl = origin + "/AOMD-Client/oauth";
        log.debug(callbackUrl);

        LoginRequest kakaoLoginResponse = kakaoAuthService.socialLogin(kakaoLoginRequest.getCode(), kakaoLoginRequest.getCallbackUrl());
        String jwt = tokenProvider.createToken(kakaoLoginResponse, 5 * 60);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity(httpHeaders, OK);
    }
    
    
}
