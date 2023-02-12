package study.movieservice.exception.custom;

import lombok.Getter;
import study.movieservice.domain.ResultEnum;

@Getter
public class NotCompleteAuthException extends RuntimeException{

    ResultEnum resultEnum;

    public NotCompleteAuthException(ResultEnum resultEnum){
        this.resultEnum=resultEnum;
    }
}
