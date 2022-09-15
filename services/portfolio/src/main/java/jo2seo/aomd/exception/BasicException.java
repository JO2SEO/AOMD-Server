package jo2seo.aomd.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BasicException extends RuntimeException {
    
    private final HttpStatus httpStatus;
    
    protected BasicException(final HttpStatus httpStatus, final String detail) {
        super(detail);
        this.httpStatus = httpStatus;
    }
}
