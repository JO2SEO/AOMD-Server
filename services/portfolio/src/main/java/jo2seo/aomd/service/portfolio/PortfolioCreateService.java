package jo2seo.aomd.service.portfolio;

import jo2seo.aomd.api.portfolio.dto.CreatePortfolioRequest;
import jo2seo.aomd.domain.Portfolio;

public interface PortfolioCreateService {

    Portfolio createNewPortfolio(CreatePortfolioRequest createPortfolioRequest);
    
    Portfolio addBlock(String shareUrl, String blockId);
}
