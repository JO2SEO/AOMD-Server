package jo2seo.aomd.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static jo2seo.aomd.exception.ExceptionType.DUPLICATED_EMAIL;
import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(CONFLICT)
public class AlreadyInMemberException extends BasicException {
    public AlreadyInMemberException() {
        super(DUPLICATED_EMAIL.getHttpStatus(), DUPLICATED_EMAIL.getDetail());
    }
}
