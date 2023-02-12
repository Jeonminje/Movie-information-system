package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.movieservice.domain.Member;
import study.movieservice.service.LoginService;
import static study.movieservice.domain.ResultEnum.*;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> loginAccess(@RequestBody Member member){
        if(loginService.loginAccess(member))
            return new ResponseEntity<>(SUCCESS_LOGIN.getText(),SUCCESS_LOGIN.getStatus());
        return new ResponseEntity<>(FAILED_LOGIN.getText(),FAILED_LOGIN.getStatus());
    }

    @PostMapping("/logout")
    public void logout(){
        loginService.logout();
    }
}
