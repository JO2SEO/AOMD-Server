package jo2seo.aomd.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotNull @Email
    private String email;
    @NotNull
    private String password;

    private String profileImgUrl;
    @NotNull
    private String nickname;
}
