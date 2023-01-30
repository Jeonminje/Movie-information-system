package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import study.movieservice.domain.member.Member;
import study.movieservice.service.MemberService;



@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @ResponseBody
    @PostMapping("/idcheck") // 아이디 중복 확인
    public String loginIdCheck(@RequestBody Member member) {
        if (memberService.checkLoginId(member.getLoginId())) {
            return "이미 사용중인 아이디 입니다.";
        } else {
            return "사용가능한 아이디 입니다.";
        }
    }

    @ResponseBody
    @PostMapping("/join") // 회원가입
    public String memberJoin(@RequestBody Member member) {
        memberService.addMember(member);
        return "ok";
    }
}
