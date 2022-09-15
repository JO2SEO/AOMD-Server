package jo2seo.aomd.service.portfolio.validation;

import jo2seo.aomd.domain.Portfolio;

public interface PortfolioValidator {

    void checkMine(String userEmail, Portfolio portfolio);

    void checkSharing(Portfolio portfolio);
}
