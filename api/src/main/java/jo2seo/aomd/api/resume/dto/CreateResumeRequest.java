package jo2seo.aomd.api.resume.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CreateResumeRequest {
    @NotNull
    private Long portfolioId;
    @NotNull
    private String question;
    @NotNull
    private String content;
}
