package study.movieservice.exception.custom;

import lombok.Getter;
import study.movieservice.domain.ResultEnum;

@Getter
public class NotCompleteException extends RuntimeException{

    ResultEnum resultEnum;

    public NotCompleteException(ResultEnum resultEnum){
        this.resultEnum=resultEnum;
    }
}
