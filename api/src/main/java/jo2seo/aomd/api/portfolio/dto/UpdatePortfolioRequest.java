package jo2seo.aomd.api.portfolio.dto;

import jo2seo.aomd.api.resume.dto.UpdateResumeRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdatePortfolioRequest {
    
    @NotNull
    private String title;

    @NotNull
    private Boolean sharing;

    @Nullable
    private List<UpdatePortfolioBlockRequest> portfolioBlockList;

    @Nullable
    private List<UpdateResumeRequest> resumeList;
}
