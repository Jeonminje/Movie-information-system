package study.movieservice.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 로그인 시, 사용되는 DTO
 */
@Getter
@RequiredArgsConstructor
public class LoginParam {
    private final String loginId;
    private final String loginPassword;
}
