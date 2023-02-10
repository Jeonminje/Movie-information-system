package study.movieservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.movieservice.domain.member.LoginParam;
import study.movieservice.domain.member.MemberDTO;
import study.movieservice.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/log-in")
    public ResponseEntity<String> login(@RequestBody LoginParam loginParam, HttpSession session){

        if(memberService.logIn(loginParam.getLoginId(), loginParam.getLoginPassword(), session)){
            return ResponseEntity.ok("로그인이 완료되었습니다.");
        }
        return new ResponseEntity<>("아이디 또는 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/log-out")
    public String logout(HttpSession session){
        memberService.logOut(session);
        return "로그아웃 되었습니다.";
    }


}
