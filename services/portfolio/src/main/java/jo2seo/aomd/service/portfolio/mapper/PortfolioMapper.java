package jo2seo.aomd.service.portfolio.mapper;

import jo2seo.aomd.api.portfolio.dto.PortfolioDto;
import jo2seo.aomd.domain.Portfolio;

public interface PortfolioMapper {
    PortfolioDto entityToDTO(Portfolio entity);
}
