package study.movieservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import study.movieservice.domain.member.Member;
import study.movieservice.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody Member member, HttpServletRequest request){

        if(memberService.checkMember(member.getLoginId(), member.getLoginPassword(), request)){
            return "로그인이 완료되었습니다.";
        }
        return "아이디 또는 비밀번호가 일치하지 않습니다.";
    }

    @ResponseBody
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false); // true면 없으면 세션 생성.
        if(session != null){
            session.invalidate();
        }
        return "로그아웃 되었습니다.";
    }


}
