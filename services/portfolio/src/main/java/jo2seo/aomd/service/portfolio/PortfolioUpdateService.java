package jo2seo.aomd.service.portfolio;

import jo2seo.aomd.api.portfolio.dto.UpdatePortfolioRequest;
import jo2seo.aomd.domain.Portfolio;
import org.springframework.transaction.annotation.Transactional;

public interface PortfolioUpdateService {
    
    Portfolio update(String shareUrl, UpdatePortfolioRequest updatePortfolioRequest);
}