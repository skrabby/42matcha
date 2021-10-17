package fr.intra.exception;

import org.springframework.http.HttpStatus;

public class LoginException extends MatchaException {

    public LoginException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
