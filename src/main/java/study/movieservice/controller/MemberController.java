package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.movieservice.domain.member.MemberDTO;
import study.movieservice.service.MailService;
import study.movieservice.service.MemberService;

import javax.security.auth.login.LoginException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final MailService mailService;

    @PostMapping("/id-check")
    public ResponseEntity<String> loginIdCheck(@RequestParam String loginId) throws LoginException {

        if (memberService.checkLoginId(loginId)) {
            throw new LoginException("이미 사용중인 아이디 입니다.");
        } else {
            return new ResponseEntity<>("사용가능한 아이디 입니다.", HttpStatus.OK);
        }
    }

    @PostMapping("/sign-up")
    public void memberJoin(@RequestBody MemberDTO memberDTO) {
        memberService.addMember(memberDTO);
    }

    @ResponseBody
    @PostMapping("/email-check")
    public ResponseEntity<String> memberEmailCheck(@RequestParam String email) {
        memberService.sendSignUpMessage(email);
        return ResponseEntity.ok("이메일 전송에 성공하였습니다.(코드 유효기간 : 3분)");
    }

    @ResponseBody
    @PostMapping("/email-code")
    public ResponseEntity<String> memberEmailCodeCheck(@RequestParam String email, @RequestParam String emailCode) {
        boolean result = mailService.checkEmailCode(email, emailCode);

        if(result){
            return ResponseEntity.ok("인증이 완료되었습니다.");
        }else{
            return new ResponseEntity<>("인증번호가 틀렸습니다. 다시 시도해주세요", HttpStatus.BAD_REQUEST);
        }
    }
}
