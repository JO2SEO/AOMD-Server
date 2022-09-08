package jo2seo.aomd.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(CONFLICT)
public class AlreadyInUserException extends RuntimeException {
    public AlreadyInUserException(String message) {
        super(message);
    }
}
