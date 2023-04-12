package study.movieservice.repository;

import org.apache.ibatis.annotations.Mapper;
import study.movieservice.domain.member.Grade;
import study.movieservice.domain.member.Member;

import java.util.Optional;

/**
 * member 테이블에 접근하는 매퍼.
 */
@Mapper
public interface MemberMapper {
    void save(Member member);
    boolean findByLoginId(String loginId);
    Optional<Member> getMemberForLogIn(String loginId);
    void changeGrade(Long memberId, Grade grade);
}