package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.movieservice.domain.Member;
import study.movieservice.service.MemberService;
import java.util.Optional;

import static study.movieservice.domain.ResultEnum.*;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<String> loginAccess(@RequestBody Member member){
        Optional<Member> receiver= memberService.findByLoginId(member.getLoginId());

        if(receiver.isPresent()){
            if(memberService.checkEmailAuth(member.getLoginId())!=1) {
                return new ResponseEntity<>(NOT_COMPLETED_AUTH.getText(),NOT_COMPLETED_AUTH.getStatus());
            }

            if(memberService.loginAccess(member)) {
                return new ResponseEntity<>(SUCCESS_LOGIN.getText(), SUCCESS_LOGIN.getStatus());
            }

            return new ResponseEntity<>(FAILED_LOGIN.getText(),FAILED_LOGIN.getStatus());
        }
        else
            return new ResponseEntity<>(CANNOT_FOUND_ID.getText(), CANNOT_FOUND_ID.getStatus());
    }

    @PostMapping("/logout")
    public void logout(){
        memberService.logout();
    }
}
