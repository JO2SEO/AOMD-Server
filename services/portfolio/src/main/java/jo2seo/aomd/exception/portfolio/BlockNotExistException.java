package jo2seo.aomd.exception.portfolio;

import jo2seo.aomd.exception.BasicException;

import static jo2seo.aomd.exception.ExceptionType.PORTFOLIO_BLOCK_NOT_EXIST;

public class BlockNotExistException extends BasicException {

    public BlockNotExistException() {
        super(PORTFOLIO_BLOCK_NOT_EXIST.getHttpStatus(), PORTFOLIO_BLOCK_NOT_EXIST.getDetail());
    }
}
