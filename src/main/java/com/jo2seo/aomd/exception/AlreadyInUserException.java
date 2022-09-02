package com.jo2seo.aomd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyInUserException extends RuntimeException {
    public AlreadyInUserException(String message) {
        super(message);
    }
}
