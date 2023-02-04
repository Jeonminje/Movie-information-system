package study.movieservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.LoginException;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String> loginHandler(LoginException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
