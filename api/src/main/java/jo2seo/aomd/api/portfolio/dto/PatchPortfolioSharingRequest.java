package jo2seo.aomd.api.portfolio.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PatchPortfolioSharingRequest {
    @NotNull
    private boolean sharing;
}
