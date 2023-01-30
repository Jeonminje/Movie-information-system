package study.movieservice.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import study.movieservice.domain.SessionConst;
import study.movieservice.domain.member.Grade;
import study.movieservice.domain.member.Member;
import study.movieservice.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;


    public void addMember(Member member){
        String encodingPassword = BCrypt.hashpw(member.getLoginPassword(), BCrypt.gensalt());

        member.setLoginPassword(encodingPassword);
        member.setGrade(Grade.BASIC);

        memberRepository.save(member);
    }

    public boolean checkLoginId(String loginId){
        Optional<Member> finding  = memberRepository.findByLoginId(loginId);

        if(finding.isPresent()){ // 값이 있다면.
            return true;
        }

        return false;
    }

    public boolean checkMember(String loginId, String password, HttpServletRequest request){

        Optional<Member> finding  = memberRepository.findByLoginId(loginId);

        if(finding.isPresent()){ // 값이 있다면.
            Member findMember = finding.get();
            if(BCrypt.checkpw(password, findMember.getLoginPassword())){

                //세션이 있으면 있는 세션을 반환, 없으면 신규 세션을 생성
                HttpSession session = request.getSession();
                //세션에 로그인 회원 정보 보관.
                session.setAttribute(SessionConst.LOGIN_MEMBER, findMember.getMemberId());

                return true;
            }
        }

        return false;
    }

}
