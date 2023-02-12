package study.movieservice.exception.custom;

import lombok.Getter;
import study.movieservice.domain.ResultEnum;

@Getter
public class NotFoundMemberException extends IllegalStateException{

    ResultEnum resultEnum;

    public NotFoundMemberException(ResultEnum resultEnum){
        this.resultEnum=resultEnum;
    }
}
