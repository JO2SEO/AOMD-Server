package jo2seo.aomd.api.resume.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDto {
    private Long portfolioId;
    private Long resumeId;
    private String question;
    private String content;
}
