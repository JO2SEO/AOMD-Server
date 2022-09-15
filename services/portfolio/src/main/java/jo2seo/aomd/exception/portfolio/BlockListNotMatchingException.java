package jo2seo.aomd.exception.portfolio;

import jo2seo.aomd.exception.BasicException;

import static jo2seo.aomd.exception.ExceptionType.PORTFOLIO_BLOCK_NOT_MATCHING;

public class BlockListNotMatchingException extends BasicException {

    public BlockListNotMatchingException() {
        super(PORTFOLIO_BLOCK_NOT_MATCHING.getHttpStatus(), PORTFOLIO_BLOCK_NOT_MATCHING.getDetail());
    }
}
