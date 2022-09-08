package jo2seo.aomd.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BasicLoginRequest implements LoginRequest {
    private String email;
    private String password;
}
