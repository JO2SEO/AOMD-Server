package jo2seo.aomd.api.portfolio.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdatePortfolioRequest {
    
    @NotNull
    private String title;

    @NotNull
    private Boolean sharing;

    @NotNull
    private List<String> order;
}
