package fr.intra.exception;

import org.springframework.http.HttpStatus;

public class JWTException extends MatchaException {

    public JWTException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
