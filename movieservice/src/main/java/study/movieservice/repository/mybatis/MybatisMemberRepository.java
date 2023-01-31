package study.movieservice.repository.mybatis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.movieservice.domain.Member;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MybatisMemberRepository implements MemberMapper {
    private final MemberMapper memberMapper;

    @Override
    public void save(Member member) {
        memberMapper.save(member);
    }

    @Override
    public Optional<Member> findByloginId(String loginId) {
        return memberMapper.findByloginId(loginId);
    }
}
