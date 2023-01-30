package study.movieservice.repository.mybatis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.movieservice.domain.Member;
import study.movieservice.repository.MemberRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MybatisMemberRepository implements MemberRepository {

    private final MemberMapper memberMapper;

    @Override
    public Member save(Member member) {
        memberMapper.save(member);
        return member;
    }

    @Override
    public Optional<Member> findByloginId(String loginId) {
        return memberMapper.findByloginId(loginId);
    }
}
