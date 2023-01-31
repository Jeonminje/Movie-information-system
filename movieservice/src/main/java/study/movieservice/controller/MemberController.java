package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.movieservice.domain.member.LoginIdDTO;
import study.movieservice.domain.member.MemberDTO;
import study.movieservice.service.MemberService;



@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/idcheck") // 아이디 중복 확인
    public ResponseEntity<String> loginIdCheck(@RequestBody LoginIdDTO loginId) {
        if (memberService.checkLoginId(loginId.getLoginId())) {
            return new ResponseEntity<>("이미 사용중인 아이디 입니다.", HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>("사용가능한 아이디 입니다.", HttpStatus.OK);
        }
    }

    @PostMapping("/join") // 회원가입
    public String memberJoin(@RequestBody MemberDTO memberDTO) {
        memberService.addMember(memberDTO);
        return "ok";
    }
}
