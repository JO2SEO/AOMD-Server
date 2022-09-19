package jo2seo.aomd.service.portfolio;

import jo2seo.aomd.api.portfolio.dto.PortfolioCompositeDto;
import jo2seo.aomd.api.portfolio.dto.PortfolioTitleDto;
import jo2seo.aomd.domain.Portfolio;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PortfolioReadService {

    @Transactional(readOnly = true)
    List<PortfolioCompositeDto> findAllPortfolioByMember(String memberEmail);

    @Transactional(readOnly = true)
    List<PortfolioTitleDto> findSimplePortfolioAllByMember(String memberEmail);

    @Transactional(readOnly = true)
    Portfolio findByUrl(String shareUrl);

    @Transactional(readOnly = true)
    Portfolio findById(Long portfolioId);

    @Transactional(readOnly = true)
    Portfolio checkMineAndGet(String memberEmail, Long portfolioId);

    @Transactional(readOnly = true)
    Portfolio checkSharingAndGet(String shareUrl);
}
