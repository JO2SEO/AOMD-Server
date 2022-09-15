package jo2seo.aomd.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static jo2seo.aomd.exception.ExceptionType.*;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String jwt = resolveToken(request);
        
        
        
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        log.debug("request = " + request + ", response = " + response + ", authException = " + authException);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=utf-8");

        ObjectMapper objectMapper = new ObjectMapper();
        boolean isFailedToLogin = authException instanceof BadCredentialsException;
        boolean isEmptyJwt = !StringUtils.hasText(jwt);
        if (isFailedToLogin) {
            objectMapper.writeValue(response.getWriter(), new ResponseEntity(MEMBER_INFORMATION_NOT_FOUND.getDetail(), MEMBER_INFORMATION_NOT_FOUND.getHttpStatus()));
        } else if (isEmptyJwt) {
                objectMapper.writeValue(response.getWriter(), new ResponseEntity(TOKEN_NOT_FOUND.getDetail(), TOKEN_NOT_FOUND.getHttpStatus()));
        } else {
            objectMapper.writeValue(response.getWriter(), new ResponseEntity(INVALID_TOKEN.getDetail(), INVALID_TOKEN.getHttpStatus()));
        }
    }

    private String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(JwtFilter.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
