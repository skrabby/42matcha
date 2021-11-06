package fr.intra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class MatchaException extends ResponseStatusException {
    private final HttpStatus httpStatus;

    protected MatchaException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus () {
        return httpStatus;
    }
}
