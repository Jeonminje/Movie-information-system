package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.movieservice.domain.Grade;
import study.movieservice.domain.Member;
import study.movieservice.repository.mybatis.MemberMapper;

@Service
@RequiredArgsConstructor
public class MemberService{
    private final MemberMapper memberMapper;

    public void save(Member member){
        member.setLoginPassword(BCrypt.hashpw(member.getLoginPassword(),BCrypt.gensalt()));
        memberMapper.save(member);
    }
}
