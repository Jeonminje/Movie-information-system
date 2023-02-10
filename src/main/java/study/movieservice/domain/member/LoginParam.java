package study.movieservice.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginParam {
    private final String loginId;
    private final String loginPassword;
}
