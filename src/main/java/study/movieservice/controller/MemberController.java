package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import study.movieservice.domain.member.MemberDTO;
import study.movieservice.service.MailService;
import study.movieservice.service.MemberService;

import static study.movieservice.domain.ExceptionMessageConst.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;

    @PostMapping("/id-check")
    @ResponseStatus(HttpStatus.OK)
    public String loginIdCheck(@RequestParam String loginId) {
        memberService.checkLoginId(loginId);
        return SUCCESS_CHECK_ID.getMessage();
    }

    @PostMapping("/email-check")
    @ResponseStatus(HttpStatus.OK)
    public String memberEmailCheck(@RequestParam String email) {
        memberService.sendSignUpMessage(email);
        return SUCCESS_SEND_EMAIL.getMessage();
    }

    @PostMapping("/email-code")
    @ResponseStatus(HttpStatus.OK)
    public String memberEmailCodeCheck(@RequestParam String email, @RequestParam String emailCode) {
        mailService.checkEmailCode(email, emailCode);
        return SUCCESS_CHECK_EMAIL.getMessage();
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.OK)
    public String memberJoin(@RequestBody MemberDTO memberDTO) {
        memberService.addMember(memberDTO);
        return SUCCESS_SIGN_UP.getMessage();
    }
}
