package jo2seo.aomd.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoLoginRequest {
    @Nullable
    private String code;
    @Nullable
    private String callbackUrl;
    
}
