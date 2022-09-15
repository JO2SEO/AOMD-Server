package jo2seo.aomd.exception.portfolio;

import jo2seo.aomd.exception.BasicException;

import static jo2seo.aomd.exception.ExceptionType.PORTFOLIO_BLOCK_NOT_EXIST;

public class BlockExistException extends BasicException {

    public BlockExistException() {
        super(PORTFOLIO_BLOCK_NOT_EXIST.getHttpStatus(), PORTFOLIO_BLOCK_NOT_EXIST.getDetail());
    }
}
