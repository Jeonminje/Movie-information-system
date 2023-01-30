package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.movieservice.domain.Grade;
import study.movieservice.domain.Member;
import study.movieservice.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberServiceV1 implements MemberService{
    private final MemberRepository memberRepository;


    @Transactional
    @Override
    public Member save(Member member){
        member.setGrade(Grade.BASIC);
        member.setLoginPassword(BCrypt.hashpw(member.getLoginPassword(),BCrypt.gensalt()));
        return memberRepository.save(member);
    }




}
