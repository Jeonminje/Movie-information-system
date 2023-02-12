package study.movieservice.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import study.movieservice.domain.Member;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    void save(Member member);

    Optional<Member> findByLoginId(String loginId);

    void updateMailAuth(Member member);

    void updateMailKey(@Param("member") Member member, @Param("newMailKey") String newMailKey);

    boolean checkEmailAuth(String loginId);

    Member loadUserInfo(String loginId);

    String findPasswordByLoginId(String loginId);
}