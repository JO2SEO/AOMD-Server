package jo2seo.aomd.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BasicException.class)
    public ResponseEntity applicationException(final BasicException e) {
        final String errorMessage = e.getMessage();
        log.warn(e.getClass().getSimpleName(), errorMessage);
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(errorMessage);
    }
}
