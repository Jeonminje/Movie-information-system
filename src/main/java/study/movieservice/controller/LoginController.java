package study.movieservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import study.movieservice.domain.member.LoginParam;
import study.movieservice.service.MemberService;

import javax.security.auth.login.LoginException;

import static study.movieservice.domain.ExceptionMessageConst.SUCCESS_LOGIN;
import static study.movieservice.domain.ExceptionMessageConst.SUCCESS_LOGOUT;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/log-in")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody LoginParam loginParam) throws LoginException {
        memberService.logIn(loginParam.getLoginId(), loginParam.getLoginPassword());
        return SUCCESS_LOGIN.getMessage();
    }

    @PostMapping("/log-out")
    @ResponseStatus(HttpStatus.OK)
    public String logout(){
        memberService.logOut();
        return SUCCESS_LOGOUT.getMessage();
    }
}