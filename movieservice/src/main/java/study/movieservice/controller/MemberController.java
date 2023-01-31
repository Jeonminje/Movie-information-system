package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import study.movieservice.domain.Member;
import study.movieservice.service.MemberService;

/*
가입완료 /member/join
중복확인 /member/idcheck
이메일 인증 /member/emailcheck
캡챠 /member/capcha
로그인 화면 /login
 */
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member/join")
    @ResponseBody
    public String join(@RequestBody Member member){

        memberService.save(member);
        return "ok";
    }


}
