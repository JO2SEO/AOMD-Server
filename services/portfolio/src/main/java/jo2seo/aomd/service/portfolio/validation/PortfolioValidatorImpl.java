package jo2seo.aomd.service.portfolio.validation;

import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.exception.portfolio.NoSharingPortfolioException;
import jo2seo.aomd.exception.portfolio.NotMyPortfolioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PortfolioValidatorImpl implements PortfolioValidator {

    @Override
    public void checkMine(String memberEmail, Portfolio portfolio) {
        if (!memberEmail.equals(portfolio.getMember().getEmail())) {
            throw new NotMyPortfolioException();
        }
    }

    @Override
    public void checkSharing(Portfolio portfolio) {
        if(!portfolio.getSharing()) throw new NoSharingPortfolioException();
    }
}
