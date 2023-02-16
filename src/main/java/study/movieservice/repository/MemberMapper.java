package study.movieservice.repository;

import org.apache.ibatis.annotations.Mapper;
import study.movieservice.domain.member.Member;

import java.util.Optional;


@Mapper
public interface MemberMapper {
    void save(Member member);
    boolean findByLoginId(String loginId);
    Optional<Member> getMemberForLogIn(String loginId);
}