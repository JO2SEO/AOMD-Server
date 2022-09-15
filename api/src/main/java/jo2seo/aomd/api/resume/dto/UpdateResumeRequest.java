package jo2seo.aomd.api.resume.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
public class UpdateResumeRequest {
    
    @Nullable
    private String question;

    @Nullable
    private String content;
}
