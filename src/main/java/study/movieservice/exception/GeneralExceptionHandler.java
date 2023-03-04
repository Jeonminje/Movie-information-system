package study.movieservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.movieservice.domain.ExceptionMessageConst;

import javax.security.auth.login.LoginException;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String IllegalArgumentHandler(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MailSendException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String mailExceptionHandler(MailSendException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MailAuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String mailAuthenticationExceptionHandler(MailAuthenticationException e) {
        return e.getMessage();
    }

    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String loginHandler(LoginException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String methodArgumentNotValidHandler(MethodArgumentNotValidException e){
        return ExceptionMessageConst.FAILED_SIGN_UP.getMessage();
    }

}
