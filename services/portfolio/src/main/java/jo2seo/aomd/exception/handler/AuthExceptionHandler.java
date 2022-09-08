package jo2seo.aomd.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class AuthExceptionHandler {
    
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity RuntimeExceptionHandler(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
    }
}
