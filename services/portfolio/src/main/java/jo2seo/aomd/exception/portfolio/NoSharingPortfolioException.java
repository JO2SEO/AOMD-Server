package jo2seo.aomd.exception.portfolio;

import jo2seo.aomd.exception.BasicException;

import static jo2seo.aomd.exception.ExceptionType.PORTFOLIO_NOT_SHARING;

public class NoSharingPortfolioException extends BasicException {

    public NoSharingPortfolioException() {
        super(PORTFOLIO_NOT_SHARING.getHttpStatus(), PORTFOLIO_NOT_SHARING.getDetail());
    }
}
