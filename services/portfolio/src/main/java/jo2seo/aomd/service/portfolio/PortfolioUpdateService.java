package jo2seo.aomd.service.portfolio;

import jo2seo.aomd.api.portfolio.dto.UpdatePortfolioRequest;
import jo2seo.aomd.domain.Portfolio;

public interface PortfolioUpdateService {
    
    Portfolio update(String memberEmail, String shareUrl, UpdatePortfolioRequest updatePortfolioRequest);
}
