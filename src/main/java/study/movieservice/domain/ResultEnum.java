package study.movieservice.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@ToString
public enum ResultEnum {
    SUCCESS_CAN_USE("사용가능한 아이디입니다."),
    FAILED_CAN_USE("중복된 아이디입니다. 다시 입력해주세요"),

    FAILED_AUTH("인증에 실패하였습니다. 메일 전송을 다시 시도해주세요"),
    SUCCESS_AUTH("인증이 완료되었습니다."),

    FAILED_SENDMAIL("메일전송에 실패하였습니다. 메일 전송을 다시 시도해주세요"),
    SUCCESS_SENDMAIL("메일전송이 완료되었습니다."),

    CANNOT_FOUND_ID("존재하지않는 아이디입니다.");

    private final String text;

    ResultEnum(String text) {
        this.text=text;
    }
}
