package study.movieservice.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ResultEnum {
    SUCCESS_CAN_USE("사용가능한 아이디입니다.", HttpStatus.OK),
    FAILED_CAN_USE("중복된 아이디입니다. 다시 입력해주세요", HttpStatus.CONFLICT),

    SUCCESS_SENDMAIL("메일전송이 완료되었습니다.", HttpStatus.OK),
    FAILED_SENDMAIL("메일전송에 실패하였습니다. 메일 전송을 다시 시도해주세요", HttpStatus.SERVICE_UNAVAILABLE),

    SUCCESS_AUTH("인증이 완료되었습니다.", HttpStatus.OK),
    FAILED_AUTH("인증에 실패하였습니다. 메일 전송을 다시 시도해주세요", HttpStatus.BAD_REQUEST),

    CANNOT_FOUND_ID("존재하지않는 아이디입니다.", HttpStatus.NOT_FOUND),

    NOT_COMPLETED_AUTH("인증을 먼저 진행해주세요",HttpStatus.SERVICE_UNAVAILABLE),

    SUCCESS_LOGIN("로그인에 성공하였습니다.", HttpStatus.OK),
    FAILED_LOGIN("로그인에 실패하였습니다. 아이디 비밀번호를 다시 확인해주세요", HttpStatus.SERVICE_UNAVAILABLE);

    private final String text;
    private final HttpStatus status;

    ResultEnum(String text, HttpStatus status) {
        this.text = text;
        this.status = status;
    }
}