package study.movieservice.repository;

import study.movieservice.domain.Member;

import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findByloginId(String loginId);
}
