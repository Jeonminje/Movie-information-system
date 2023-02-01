package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.movieservice.domain.member.MemberDTO;
import study.movieservice.service.MemberService;

import javax.security.auth.login.LoginException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

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
}
