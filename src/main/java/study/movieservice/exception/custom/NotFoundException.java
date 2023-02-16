package study.movieservice.exception.custom;

import lombok.Getter;
import study.movieservice.domain.ResultEnum;

@Getter
public class NotFoundException extends IllegalStateException{

    ResultEnum resultEnum;

    public NotFoundException(ResultEnum resultEnum){
        this.resultEnum=resultEnum;
    }
}
