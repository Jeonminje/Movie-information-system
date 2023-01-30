package study.movieservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.movieservice.domain.member.Member;
import study.movieservice.repository.mapper.MemberMapper;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository{

    private final MemberMapper memberMapper;

    @Override
    public void save(Member member) {
        memberMapper.save(member);
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return memberMapper.findByLoginId(loginId);
    }
}
