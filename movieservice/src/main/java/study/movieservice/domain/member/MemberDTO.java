package study.movieservice.domain.member;

import lombok.Getter;

@Getter
public class MemberDTO {
    private String email;
    private String loginId;
    private String loginPassword;
    private String nickname;
}
