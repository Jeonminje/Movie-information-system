package study.movieservice.domain.member;

import lombok.Getter;

@Getter
public class Member {
    private Long memberId;
    private String email;
    private String loginId;
    private String loginPassword;
    private String nickname;
    private Grade grade;


    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
