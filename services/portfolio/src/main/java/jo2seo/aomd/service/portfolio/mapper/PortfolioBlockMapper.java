package jo2seo.aomd.service.portfolio.mapper;

import jo2seo.aomd.api.portfolio.dto.PortfolioBlockDto;
import jo2seo.aomd.domain.PortfolioBlock;

import java.util.Optional;

public interface PortfolioBlockMapper {
    PortfolioBlockDto entityToDTO(PortfolioBlock portfolioBlock);
}
