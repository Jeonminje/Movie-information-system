package study.movieservice.domain;


import lombok.Getter;
import lombok.Setter;

import static study.movieservice.domain.Grade.BASIC;


@Getter
@Setter
public class Member {


    private Long id;
    private String loginId;
    private String email;
    private String loginPassword;
    private String nickname;
    private Grade grade;

    public Member(){
    }

    public Member(String loginId, String loginPassword, String email, String nickname) {
        this.loginId = loginId;
        this.loginPassword = loginPassword;
        this.email = email;
        this.nickname = nickname;
        this.grade= BASIC;
    }
}
