package jo2seo.aomd.service.portfolio.mapper;

import jo2seo.aomd.api.portfolio.dto.PortfolioDto;
import jo2seo.aomd.domain.Portfolio;
import org.springframework.stereotype.Component;

@Component
public class PortfolioMapperImpl implements PortfolioMapper {
    @Override
    public PortfolioDto entityToDTO(Portfolio entity) {
        if (entity == null) {
            return null;
        }

        PortfolioDto portfolioDto = new PortfolioDto();
        portfolioDto.setPortfolioId(entity.getId());
        portfolioDto.setMemberId(entity.getMember().getId());
        portfolioDto.setTitle(entity.getTitle());
        portfolioDto.setSharing(entity.getSharing());
        portfolioDto.setShareUrl(entity.getShareUrl());
        portfolioDto.setCreatedAt(entity.getCreatedAt());
        portfolioDto.setUpdatedAt(entity.getUpdatedAt());

        return portfolioDto;
    }
}
