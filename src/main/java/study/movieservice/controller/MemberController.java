package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.movieservice.domain.Member;
import study.movieservice.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public String join(@RequestBody Member member){
        memberService.save(member);
        return "ok";
    }
}
