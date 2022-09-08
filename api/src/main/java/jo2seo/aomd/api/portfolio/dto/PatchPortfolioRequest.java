package jo2seo.aomd.api.portfolio.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;

@Getter
@NoArgsConstructor
public class PatchPortfolioRequest {
    @Nullable
    private String title;

    @Nullable
    private Boolean sharing;

    @Nullable
    private List<String> order;
}
