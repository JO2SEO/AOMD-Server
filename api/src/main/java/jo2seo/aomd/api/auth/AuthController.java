package jo2seo.aomd.api.auth;

import io.swagger.v3.oas.annotations.Operation;
import jo2seo.aomd.api.auth.dto.BasicLoginRequest;
import jo2seo.aomd.api.auth.dto.KakaoLoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;


@RequestMapping("/api/v1")
public interface AuthController {

    @Operation(
            summary = "일반 로그인",
            description = "이메일과 패스워드를 입력받아서 로그인 시도", tags = "auth")
    @PostMapping(
            value = "/auth/login",
            consumes = "application/json")
    ResponseEntity login(
            @Valid @RequestBody BasicLoginRequest basicLoginRequest,
            HttpServletResponse response
    );

    @PostMapping(
            value = "/auth/kakao",
            consumes = "application/json")
    ResponseEntity kakaoLogin(
            HttpServletRequest request,
            @Valid @RequestBody KakaoLoginRequest kakaoLoginRequest
    ) throws IOException;
}
