package jo2seo.aomd.service.portfolio;

import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.exception.portfolio.NoSharingPortfolioException;
import jo2seo.aomd.exception.portfolio.NotMyPortfolioException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PortfolioValidatorImpl implements PortfolioValidator {

    @Override
    public void checkMine(String userEmail, Portfolio portfolio) {
        if (!userEmail.equals(portfolio.getMember().getEmail())) {
            throw new NotMyPortfolioException();
        }
    }

    @Override
    public void checkSharing(Portfolio portfolio) {
        if(!portfolio.getSharing()) throw new NoSharingPortfolioException();
    }
}
