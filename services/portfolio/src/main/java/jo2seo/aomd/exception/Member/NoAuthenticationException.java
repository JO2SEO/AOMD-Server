package jo2seo.aomd.exception.Member;

import jo2seo.aomd.exception.BasicException;

import static jo2seo.aomd.exception.ExceptionType.USER_INFORMATION_NOT_FOUND;

public class NoAuthenticationException extends BasicException {

    public NoAuthenticationException() {
        super(USER_INFORMATION_NOT_FOUND.getHttpStatus(), USER_INFORMATION_NOT_FOUND.getDetail());
    }
}
