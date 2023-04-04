package study.movieservice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ExceptionMessage를 가지고 있는 enum
 */
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
    NEED_LOGIN("로그인이 필요한 서비스입니다."),

    SUCCESS_SIGN_UP("회원가입이 완료되었습니다."),
    FAILED_SIGN_UP("회원가입에 실패하였습니다. 입력값들을 형식에 맞춰서 확인해주세요"),
    FAILED_DATA_TRANSMISSION_SERVICE("데이터 전송중 오류가 발생했습니다. 다시시도해주세요"),

    SUCCESS_SAVE_POSTER("포스터등록이 완료되었습니다."),
    FAILED_FILE_RECEIVE("파일을 불러오는데 실패했습니다. 다시한번 확인해주세요"),

    SUCCESS_RECOMMEND_JOIN("입력이 완료되었습니다."),
    SUCCESS_RECOMMEND_UPDATE("변경이 완료되었습니다."),
    SUCCESS_RECOMMEND_DELETE("취소가 완료되었습니다."),
    FAILED_RECOMMEND_REQUEST("새로고침 후 다시 시도해주세요"),

    SUCCESS_SAVE_REVIEW("리뷰 등록이 완료되었습니다."),
    SUCCESS_DELETE_REVIEW("리뷰가 삭제되었습니다."),
    FAILED_DELETE_REVIEW("리뷰를 삭제할 수 없습니다."),
    FAILED_BRING_REVIEW("리뷰 불러오기에 실패했습니다."),
    INVALID_STATUS("status의 value를 확인해주세요"),
    FAILED_BRING_DATA("데이터를 가져오는데 실패했습니다."),

    SUCCESS_SAVE_MOVIE("영화등록이 완료되었습니다."),
    ILLEGAL_MOVIE_ID("영화가 존재하지 않습니다."),
    FAILED_GET_LOCK("현재 사용량이 많아 대기시간이 만료되었습니다."),

    SUCCESS_CHANGE_GRADE("등급 변경이 완료되었습니다.");


    private final String message;
}
