package study.movieservice.domain.member;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Builder;

import java.util.Objects;

/**
 * 멤버 객체 저장을 위한 클래스
 */
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

    /**
     * member 객체의 모든 element 값들이 같다면 equals를 만족하도록 재정의
     *
     * @param object 비교를 위한 Object 객체
     * @return member 객체의 각 요소들이 모두같으면 1 아니면 0
     */
    @Override
    public boolean equals(Object object){
        if(this==object)
            return true;
        if(!(object instanceof Member))
            return false;
        Member member=(Member) object;

        return member.email.equals(email)
                && member.loginId.equals(loginId)
                && member.loginPassword.equals(loginPassword)
                && member.nickname.equals(nickname)
                && member.grade.equals(grade);
    }

    /**
     * member 객체의 모든 element 값들이 같다면 같은 hashcode값을 리턴하도록 재정의
     *
     * @return hashcode값
     */
    @Override
    public int hashCode(){
        return Objects.hash(email,loginId,loginPassword,nickname,grade);
    }
}
