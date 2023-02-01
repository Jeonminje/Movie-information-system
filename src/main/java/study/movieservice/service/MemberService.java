package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import study.movieservice.domain.Member;
import study.movieservice.repository.mybatis.MemberMapper;

@Service
@RequiredArgsConstructor
public class MemberService{
    private final MemberMapper memberMapper;

    public void save(Member member){
        Member member1= Member.builder()
                .loginId(member.getLoginId())
                .loginPassword(BCrypt.hashpw(member.getLoginPassword(),BCrypt.gensalt()))
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
        memberMapper.save(member1);
    }
}