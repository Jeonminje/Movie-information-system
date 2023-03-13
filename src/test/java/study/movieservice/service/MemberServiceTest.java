package study.movieservice.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.movieservice.domain.member.Grade;
import study.movieservice.domain.member.Member;
import study.movieservice.domain.member.MemberDTO;
import study.movieservice.repository.MemberMapper;
import javax.security.auth.login.LoginException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberMapper memberMapper;
    @Mock
    SessionManager sessionManager;
    @InjectMocks
    MemberService memberService;

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    String testId="testId";
    String testEmail="test@naver.com";

    @Test
    @DisplayName("회원가입 성공할때")
    public void addMember() {
        MemberDTO memberDTO=new MemberDTO(testEmail,testId,"20","test");

        doNothing().when(memberMapper).save(any(Member.class));
        memberService.addMember(memberDTO);

        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(memberDTO);

        verify(memberMapper, times(1)).save(any(Member.class));
        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("회원가입 실패할때_컨트롤러에서 전달되는 memberDTO가 정상이 아닐때")
    public void addMember_FAILED(){
        MemberDTO memberDTO=new MemberDTO(testEmail,testId,"2","1");

        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(memberDTO);

        assertThat(violations.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("입력아이디가 이미존재할때")
    void checkLoginId_Exist() {
        doReturn(true).when(memberMapper).findByLoginId(testId);

        assertThatThrownBy(()->memberService.checkLoginId(testId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("입력아이디가 존재하지않을때")
    void checkLoginId_Not_Exist() {
        doReturn(false).when(memberMapper).findByLoginId(testId);

        memberService.checkLoginId(testId);

        verify(memberMapper,times(1)).findByLoginId(anyString());
    }

    @Test
    @DisplayName("아이디 비밀번호가 일치할때")
    void logIn_Correspond() throws LoginException {
        Member member=member();
        Member encodedMember=encodedMember();

        doReturn(Optional.of(encodedMember)).when(memberMapper).getMemberForLogIn(testId);
        doNothing().when(sessionManager).storeLoginId(1L);

        memberService.logIn(testId,member.getLoginPassword());

        verify(sessionManager,times(1)).storeLoginId(anyLong());
    }

    @Test
    @DisplayName("아이디 비밀번호가 일치하지않을때")
    void logIn_Not_Correspond(){
        Member member=member();

        doReturn(Optional.empty()).when(memberMapper).getMemberForLogIn(testId);

        assertThatThrownBy(()->memberService.logIn(testId,member.getLoginPassword()))
                .isInstanceOf(LoginException.class);

        verify(memberMapper,times(1)).getMemberForLogIn(anyString());
        verify(sessionManager,times(0)).storeLoginId(anyLong());
    }

    private Member member(){
        return Member.builder()
                .memberId(1L)
                .email(testEmail)
                .loginId(testId)
                .loginPassword("20")
                .nickname("test")
                .grade(Grade.BASIC).build();
    }

    private Member encodedMember(){
        return Member.builder()
                .memberId(1L)
                .email(testEmail)
                .loginId(testId)
                .loginPassword(BCrypt.hashpw("20",BCrypt.gensalt()))
                .nickname("test")
                .grade(Grade.BASIC).build();
    }
}