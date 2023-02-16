package study.movieservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.movieservice.exception.custom.NotCompleteException;
import study.movieservice.exception.custom.NotFoundException;

@RestControllerAdvice
public class ManageExceptionHandler {

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<String> sendmailException(MailSendException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NotCompleteException.class)
    public ResponseEntity<String> notCompleteAuthException(NotCompleteException e){
        return new ResponseEntity<>(e.getResultEnum().getText(),e.getResultEnum().getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundMemberException(NotFoundException e){
        return new ResponseEntity<>(e.getResultEnum().getText(),e.getResultEnum().getStatus());
    }
}
