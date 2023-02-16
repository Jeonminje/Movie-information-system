package study.movieservice.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberDTO {
    private final String email;
    private final String loginId;
    private final String loginPassword;
    private final String nickname;
}
