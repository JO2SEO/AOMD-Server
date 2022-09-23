package jo2seo.aomd.api.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioBlockDto {
    private Long portfolioId;
    private String blockId;
}
