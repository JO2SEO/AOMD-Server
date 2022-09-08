package jo2seo.aomd.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jo2seo.aomd.api.auth.dto.BasicLoginRequest;
import jo2seo.aomd.api.auth.dto.LoginRequest;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.UserRole;
import jo2seo.aomd.file.FileService;
import jo2seo.aomd.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Service("kakaoAuthService")
@RequiredArgsConstructor
@Transactional
public class KakaoAuthService implements AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    /*
    TODO: 이미지 서버에 이미지 업로드
    */
    public LoginRequest socialLogin(final String authorizationCode, final String callbackUrl) throws IOException {
        String kakaoAccessToken = getSocialAccessToken(authorizationCode, callbackUrl);
        Map kakaoUserMap = getSocialUser(kakaoAccessToken);
        Map profile = (Map) kakaoUserMap.get("profile");

        String email = (String) kakaoUserMap.get("email");
        String password = email + "kakaoLogin";
        String kakaoImgUrl = (String) profile.get("thumbnail_image_url");
        String nickname = (String) profile.get("nickname");
        String imgUrl = fileService.downloadKakaoProfileImage(kakaoImgUrl);

        if (memberRepository.findMemberByEmail(email).isEmpty()) {
            memberRepository.save(new Member(email, passwordEncoder.encode(password), imgUrl, nickname, UserRole.USER));
        }

        return new BasicLoginRequest(email, password);
    }

    public String getSocialAccessToken(String authorizationCode, String callbackUrl) throws JsonProcessingException {
        /* 카카오 토큰 발급 */
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "5ea91fb99e9476b7c17cf33a8c3cbb66");
        params.add("redirect_uri", callbackUrl);
        params.add("code", authorizationCode);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        RestTemplate rt = new RestTemplate();

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                entity,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        Map json = objectMapper.readValue(response.getBody(), Map.class);
        return (String)json.get("access_token");
    }

    public Map getSocialUser(final String accessToken) throws JsonProcessingException {
        /* 카카오 토큰으로 정보 가져오기 */
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", accessToken));
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                entity,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        Map json = objectMapper.readValue(response.getBody(), Map.class);
        return (Map) json.get("kakao_account");
    }
}
