package jo2seo.aomd.service.portfolio.mapper;

import jo2seo.aomd.api.portfolio.dto.PortfolioBlockDto;
import jo2seo.aomd.domain.PortfolioBlock;
import org.springframework.stereotype.Component;

@Component
public class PortfolioBlockMapperImpl implements PortfolioBlockMapper {
    @Override
    public PortfolioBlockDto entityToDTO(PortfolioBlock portfolioBlock) {
        if (portfolioBlock == null) {
            return null;
        }

        PortfolioBlockDto portfolioBlockDto = new PortfolioBlockDto();
        portfolioBlockDto.setPortfolioId(portfolioBlockDto.getPortfolioId());
        portfolioBlockDto.setBlockId(portfolioBlock.getBlockId());

        return portfolioBlockDto;
    }
}
