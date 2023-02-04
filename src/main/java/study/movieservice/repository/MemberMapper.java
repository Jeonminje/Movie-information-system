package study.movieservice.repository;

import org.apache.ibatis.annotations.Mapper;
import study.movieservice.domain.member.Member;



@Mapper
public interface MemberMapper {
    void save(Member member);
    boolean findByLoginId(String loginId);
}