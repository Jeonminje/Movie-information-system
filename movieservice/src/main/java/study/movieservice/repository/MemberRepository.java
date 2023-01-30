package study.movieservice.repository;

import study.movieservice.domain.member.Member;

import java.util.Optional;

public interface MemberRepository {
    void save(Member member);
    Optional<Member> findByLoginId(String loginId);
}
