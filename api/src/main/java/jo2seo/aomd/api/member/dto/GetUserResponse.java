package jo2seo.aomd.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetUserResponse {
    private Long id;
    private String nickname;
}
