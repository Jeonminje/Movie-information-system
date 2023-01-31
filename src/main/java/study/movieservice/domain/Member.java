package study.movieservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static study.movieservice.domain.Grade.BASIC;


@Getter

@NoArgsConstructor
public class Member {
    private Long id;
    private String loginId;
    private String email;
    private String loginPassword;
    private String nickname;
    private Grade grade;

    @Builder
    public Member(String loginId, String loginPassword, String email, String nickname) {
        this.loginId = loginId;
        this.loginPassword = loginPassword;
        this.email = email;
        this.nickname = nickname;
    }

}
