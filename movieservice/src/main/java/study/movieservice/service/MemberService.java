package study.movieservice.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import study.movieservice.domain.member.Grade;
import study.movieservice.domain.member.LoginIdDTO;
import study.movieservice.domain.member.Member;
import study.movieservice.domain.member.MemberDTO;
import study.movieservice.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;


    public void addMember(MemberDTO memberDTO){
        String encodingPassword = BCrypt.hashpw(memberDTO.getLoginPassword(), BCrypt.gensalt());
        Member member = new Member(
                memberDTO.getEmail(),
                memberDTO.getLoginId(),
                encodingPassword,
                memberDTO.getNickname(),
                Grade.BASIC);
        memberRepository.save(member);
    }

    public boolean checkLoginId(String loginId){
        Optional<LoginIdDTO> finding  = memberRepository.findByLoginId(loginId);

        if(finding.isPresent()){ // 값이 있다면.
            return true;
        }

        return false;
    }

}
