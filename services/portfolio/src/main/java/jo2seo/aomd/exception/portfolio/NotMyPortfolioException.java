package jo2seo.aomd.exception.portfolio;

import jo2seo.aomd.exception.BasicException;
import org.springframework.http.HttpStatus;

import static jo2seo.aomd.exception.ExceptionType.PORTFOLIO_NOT_MINE;

public class NotMyPortfolioException extends BasicException {

    public NotMyPortfolioException() {
        super(PORTFOLIO_NOT_MINE.getHttpStatus(), PORTFOLIO_NOT_MINE.getDetail());
    }
}
