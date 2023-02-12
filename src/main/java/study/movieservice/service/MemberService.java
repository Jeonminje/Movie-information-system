package study.movieservice.service;


import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import study.movieservice.domain.member.Grade;
import study.movieservice.domain.member.Member;
import study.movieservice.domain.member.MemberDTO;
import study.movieservice.exception.LoginIncorrectException;
import study.movieservice.repository.MemberMapper;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final MailService mailService;
    private final SessionManager sessionManager;

    public void addMember(MemberDTO memberDTO){
        String encodingPassword = BCrypt.hashpw(memberDTO.getLoginPassword(), BCrypt.gensalt());
        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .loginId(memberDTO.getLoginId())
                .loginPassword(encodingPassword)
                .nickname(memberDTO.getNickname())
                .grade(Grade.BASIC).build();
        memberMapper.save(member);
    }

    public boolean checkLoginId(String loginId){
        boolean findFlag  = memberMapper.findByLoginId(loginId);

        if(findFlag){
            return true;
        }

        return false;
    }

    public void sendSignUpMessage(String email){
        String emailCode = mailService.createKey();
        MimeMessage message = mailService.createSignUpMessage(email, emailCode);
        mailService.sendMail(message);
    }

    public void logIn(String loginId, String password){

        Optional<Member> finding  = memberMapper.getMemberForLogIn(loginId);

        if(finding.isPresent()){
            Member findMember = finding.get();
            if(BCrypt.checkpw(password, findMember.getLoginPassword())){

                sessionManager.storeLoginIdAndNickname(findMember.getLoginId(), findMember.getNickname());
                return;
            }
        }
        throw new LoginIncorrectException();
    }

    public void logOut(){
        sessionManager.deleteAll();
    }
}
