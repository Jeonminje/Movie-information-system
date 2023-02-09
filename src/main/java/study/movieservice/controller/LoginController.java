package study.movieservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.movieservice.domain.member.MemberDTO;
import study.movieservice.service.MemberService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/log-in")
    public String login(@RequestBody MemberDTO member, HttpServletRequest request){

        if(memberService.logIn(member.getLoginId(), member.getLoginPassword(), request)){
            return "로그인이 완료되었습니다.";
        }
        return "아이디 또는 비밀번호가 일치하지 않습니다.";
    }

    @PostMapping("/log-out")
    public String logout(@RequestParam String loginId, HttpServletRequest request){
        memberService.logOut(loginId, request);
        return "로그아웃 되었습니다.";
    }


}
