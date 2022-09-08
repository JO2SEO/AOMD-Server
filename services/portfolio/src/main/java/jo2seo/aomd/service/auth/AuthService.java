package jo2seo.aomd.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import jo2seo.aomd.api.auth.dto.LoginRequest;

import java.io.IOException;
import java.util.Map;

public interface AuthService {
    
    LoginRequest socialLogin(final String authorizationCode, final String callbackUrl) throws IOException;

    String getSocialAccessToken(String authorizationCode, String callbackUrl) throws JsonProcessingException;
    
    Map getSocialUser(final String accessToken) throws JsonProcessingException;
    
}
