package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.movieservice.domain.Member;
import study.movieservice.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody Member member){
        memberService.save(member);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/id-check")
    public ResponseEntity<String> isId_Duplicated(@RequestParam String loginId){
        if(memberService.findByLoginId(loginId))
            return new ResponseEntity<>("중복된 아이디입니다. 다시 입력해주세요", HttpStatus.CONFLICT);
        return new ResponseEntity<>("사용가능한 아이디입니다.",HttpStatus.OK);
    }
}
