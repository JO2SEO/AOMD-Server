package jo2seo.aomd.exception.Member;

import jo2seo.aomd.exception.BasicException;

import static jo2seo.aomd.exception.ExceptionType.MEMBER_NOT_FOUND;

public class MemberNotFoundException extends BasicException {
    public MemberNotFoundException() {
        super(MEMBER_NOT_FOUND.getHttpStatus(), MEMBER_NOT_FOUND.getDetail());
    }
}
