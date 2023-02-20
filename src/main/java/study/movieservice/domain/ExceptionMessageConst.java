package study.movieservice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessageConst {

    SUCCESS_CHECK_ID("사용가능한 아이디 입니다."),
    FAILED_CHECK_ID("이미 사용중인 아이디 입니다."),

    SUCCESS_SEND_EMAIL("이메일 전송에 성공하였습니다.(코드 유효기간 : 3분)"),
    FAILED_SEND_EMAIL("이메일 전송에 실패하였습니다."),
    FAILED_WRITE_EMAIL("이메일 작성에 실패하였습니다."),
    SUCCESS_CHECK_EMAIL("이메일 인증이 완료되었습니다."),
    FAILED_CHECK_EMAIL("인증번호가 틀렸습니다. 다시 시도해주세요"),

    SUCCESS_LOGIN("로그인이 완료되었습니다."),
    FAILED_LOGIN("아이디 또는 비밀번호가 일치하지 않습니다."),
    SUCCESS_LOGOUT("로그아웃 되었습니다."),

    SUCCESS_SIGN_UP("회원가입이 완료되었습니다."),

    SUCCESS_SAVE_MOVIE("영화등록이 완료되었습니다."),
    SUCCESS_SAVE_POSTER("포스터등록이 완료되었습니다."),
    FAILED_FILE_RECEIVE("파일을 불러오는데 실패했습니다. 다시한번 확인해주세요"),

    SUCCESS_RECOMMEND_UPDATE("입력이 완료되었습니다.");

    private final String message;
}
