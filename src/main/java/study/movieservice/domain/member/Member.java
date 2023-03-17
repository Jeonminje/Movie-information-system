package study.movieservice.domain.member;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Builder;

import java.util.Objects;

@Builder
@Getter
@EqualsAndHashCode
public class Member {
    private final Long memberId;
    private final String email;
    private final String loginId;
    private final String loginPassword;
    private final String nickname;
    private final Grade grade;

    @Builder
    public Member(Long memberId, String email, String loginId, String loginPassword, String nickname, Grade grade) {
        this.memberId = memberId;
        this.email = email;
        this.loginId = loginId;
        this.loginPassword = loginPassword;
        this.nickname = nickname;
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o){
        if(this==o)
            return true;
        if(!(o instanceof Member))
            return false;
        Member member=(Member) o;

        return member.email.equals(email)
                && member.loginId.equals(loginId)
                && member.loginPassword.equals(loginPassword)
                && member.nickname.equals(nickname)
                && member.grade.equals(grade);
    }
    @Override
    public int hashCode(){
        return Objects.hash(email,loginId,loginPassword,nickname,grade);
    }
}
