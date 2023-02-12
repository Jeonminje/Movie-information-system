package study.movieservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import study.movieservice.exception.LoginIncorrectException;
import study.movieservice.exception.LoginSetAttributeException;
import study.movieservice.exception.LogoutException;

import javax.security.auth.login.LoginException;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String> loginHandler(LoginException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<String> mailExceptionHandler(MailSendException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(LoginSetAttributeException.class)
    public ResponseEntity<String> loginSetAttributeExceptionHandler(LoginSetAttributeException e){
        return new ResponseEntity<>("로그인에 실패하였습니다.", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(LoginIncorrectException.class)
    public ResponseEntity<String> logoutExceptionHandler(LoginIncorrectException e){
        return new ResponseEntity<>("아이디 또는 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LogoutException.class)
    public ResponseEntity<String> logoutExceptionHandler(LogoutException e){
        return new ResponseEntity<>("로그아웃에 실패하였습니다.", HttpStatus.METHOD_NOT_ALLOWED);
    }
}
