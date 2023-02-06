package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.movieservice.domain.Member;
import study.movieservice.service.MailService;
import study.movieservice.service.MemberService;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final MailService mailService;

    @PostMapping("/join")
    public void join(@RequestBody Member member){
        memberService.save(member);
    }

    @PostMapping("/id-check")
    public ResponseEntity<String> isIdDuplicated(@RequestParam String loginId){
        if(memberService.findByLoginId(loginId).isEmpty())
            return new ResponseEntity<>("중복된 아이디입니다. 다시 입력해주세요", HttpStatus.CONFLICT);
        return new ResponseEntity<>("사용가능한 아이디입니다.",HttpStatus.OK);
    }

    @PostMapping("/email-auth")
    public void emailAuth(@RequestBody Member member){
        Optional<Member> receiver=memberService.findByLoginId(member.getLoginId());
        if(receiver.isPresent())
            mailService.sendEmailAuth(receiver.get());
    }

    @GetMapping("/confirm-email")
    public void confirmEmail(@RequestParam String loginId,@RequestParam String email, @RequestParam("email_key") String emailKey){
        Optional<Member> receiver=memberService.findByLoginId(loginId);
        if(receiver.isPresent())
            mailService.updateMailAuth(receiver.get(),email,emailKey);
    }
}
