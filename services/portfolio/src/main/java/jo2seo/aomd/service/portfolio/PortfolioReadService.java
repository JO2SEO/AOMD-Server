package jo2seo.aomd.service.portfolio;

import jo2seo.aomd.api.portfolio.dto.GetAllPortfolioTitle;
import jo2seo.aomd.domain.Portfolio;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PortfolioReadService {

    @Transactional(readOnly = true)
    List<GetAllPortfolioTitle> findAll();

    @Transactional(readOnly = true)
    Portfolio findByUrl(String shareUrl);

    @Transactional(readOnly = true)
    Portfolio findById(Long portfolioId);

    @Transactional(readOnly = true)
    Portfolio checkMineAndGet(String userEmail, Long portfolioId);

    @Transactional(readOnly = true)
    Portfolio checkSharingAndGet(String shareUrl);
}
