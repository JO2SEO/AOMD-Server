package jo2seo.aomd.exception.portfolio;

import jo2seo.aomd.exception.BasicException;

import static jo2seo.aomd.exception.ExceptionType.PORTFOLIO_NOT_FOUND;

public class NoMatchingPortfolioException extends BasicException {
    
    public NoMatchingPortfolioException() {
        super(PORTFOLIO_NOT_FOUND.getHttpStatus(), PORTFOLIO_NOT_FOUND.getDetail());
    }
}
