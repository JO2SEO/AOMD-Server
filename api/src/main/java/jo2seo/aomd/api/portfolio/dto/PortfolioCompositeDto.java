package jo2seo.aomd.api.portfolio.dto;

import jo2seo.aomd.api.portfolio.block.dto.BlockCompositeDto;
import jo2seo.aomd.api.resume.dto.ResumeDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PortfolioCompositeDto {
    private PortfolioDto portfolioDto;
    private BlockCompositeDto blockCompositeDto;
    private List<ResumeDto> resumeDtoList;
}
