package study.movieservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.movieservice.domain.member.LoginParam;
import study.movieservice.service.MemberService;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/log-in")
    public ResponseEntity<String> login(@RequestBody LoginParam loginParam){
        memberService.logIn(loginParam.getLoginId(), loginParam.getLoginPassword());
        return ResponseEntity.ok("로그인이 완료되었습니다.");
    }

    @PostMapping("/log-out")
    public String logout(){
        memberService.logOut();
        return "로그아웃 되었습니다.";
    }


}