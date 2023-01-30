package study.movieservice.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import study.movieservice.domain.member.Member;

import java.util.Optional;

@Mapper
public interface MemberMapper {
    public void save(Member member);
    public Optional<Member> findByLoginId(String loginId);
}