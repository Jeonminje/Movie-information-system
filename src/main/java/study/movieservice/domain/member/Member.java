package study.movieservice.domain.member;

import lombok.Getter;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
public class Member {
    private final Long memberId;
    private final String email;
    private final String loginId;
    private final String loginPassword;
    private final String nickname;
    private final Grade grade;
}
