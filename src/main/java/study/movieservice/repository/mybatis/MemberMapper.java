package study.movieservice.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import study.movieservice.domain.Member;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    void save(Member member);

    Optional<Member> findByLoginId(String loginId);

    void updateMailAuth(Member member);
}