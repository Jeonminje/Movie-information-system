package study.movieservice.service;


import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import study.movieservice.domain.member.Grade;
import study.movieservice.domain.member.Member;
import study.movieservice.domain.member.MemberDTO;
import study.movieservice.repository.MemberMapper;

import javax.mail.internet.MimeMessage;
import javax.security.auth.login.LoginException;
import javax.validation.Valid;
import java.util.Optional;

import static study.movieservice.domain.ExceptionMessageConst.FAILED_CHECK_ID;
import static study.movieservice.domain.ExceptionMessageConst.FAILED_LOGIN;

/**
 * 회원 관련 로직 클래스
 */
@Service
@RequiredArgsConstructor
@Validated
public class MemberService {
    /**
     * spring bean 자동 주입 필드
     */
    private final MemberMapper memberMapper;
    private final MailService mailService;
    private final SessionManager sessionManager;

    /**
     * 회원가입 시 호출
     * @param memberDTO 회원정보
     */
    public void addMember(@Valid MemberDTO memberDTO){
        String encodingPassword = BCrypt.hashpw(memberDTO.getLoginPassword(), BCrypt.gensalt());
        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .loginId(memberDTO.getLoginId())
                .loginPassword(encodingPassword)
                .nickname(memberDTO.getNickname())
                .grade(Grade.BASIC).build();
        memberMapper.save(member);
    }

    /**
     * 아이디 중복 여부 확인
     * @param loginId 로그인 아이디
     */
    public void checkLoginId(String loginId) {
        boolean findFlag  = memberMapper.findByLoginId(loginId);

        if(findFlag){
            throw new IllegalArgumentException(FAILED_CHECK_ID.getMessage());
        }
    }

    /**
     * 이메일 인증 메시지 전송 함수
     * @param email 이메일 주소
     */
    public void sendSignUpMessage(String email){
        String emailCode = mailService.createKey();
        MimeMessage message = mailService.createSignUpMessage(email, emailCode);
        mailService.sendMail(message);
    }

    /**
     * 로그인 처리 함수
     * @param loginId 로그인 아이디
     * @param password 로그인 패스워드
     * @throws LoginException 아이디 또는 비밀번호가 일치하지 않는 경우
     */
    public void logIn(String loginId, String password) throws LoginException {

        Optional<Member> finding  = memberMapper.getMemberForLogIn(loginId);

        if(finding.isPresent()){
            Member findMember = finding.get();
            if(BCrypt.checkpw(password, findMember.getLoginPassword())){

                sessionManager.storeLoginId(findMember.getMemberId());
                return;
            }
        }
        throw new LoginException(FAILED_LOGIN.getMessage());
    }

    /**
     * 로그아웃 처리 함수
     */
    public void logOut(){
        sessionManager.deleteAll();
    }
}
