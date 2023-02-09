package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.movieservice.domain.Member;
import study.movieservice.domain.ResultEnum;
import study.movieservice.service.MailService;
import study.movieservice.service.MemberService;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
import static study.movieservice.domain.ResultEnum.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;

    @PostMapping("/join")
    public void join(@RequestBody Member member) {
        memberService.save(member);
    }

    @PostMapping("/id-check")
    public ResponseEntity<String> isIdDuplicated(@RequestBody Map<String,String> loginIdMap) {
        if (memberService.findByLoginId(loginIdMap.get("loginId")).isEmpty()) {
            return new ResponseEntity<>(SUCCESS_CAN_USE.getText(), OK);
        }
        return new ResponseEntity<>(FAILED_CAN_USE.getText(), CONFLICT);
    }

    @PostMapping("/email-auth")
    public ResponseEntity<String> emailAuth(@RequestBody Member member) {
        Optional<Member> receiver = memberService.findByLoginId(member.getLoginId());

        if (receiver.isPresent()) {
            if (mailService.sendEmail(receiver.get(),"회원가입인증"))
                return new ResponseEntity<>(SUCCESS_SENDMAIL.getText(), OK);
            else
                return new ResponseEntity<>(FAILED_SENDMAIL.getText(), SERVICE_UNAVAILABLE);
        }
        else
            return new ResponseEntity<>(CANNOT_FOUND_ID.getText(), NOT_FOUND);
    }

    @GetMapping("/confirm-email")
    public ResponseEntity<String> confirmEmail(@RequestParam String loginId, @RequestParam String email, @RequestParam String emailKey) {
        Optional<Member> receiver = memberService.findByLoginId(loginId);

        if (receiver.isPresent()) {
            if (mailService.updateMailAuth(receiver.get(), email, emailKey))
                return new ResponseEntity<>(SUCCESS_AUTH.getText(),OK);
            else
                return new ResponseEntity<>(FAILED_AUTH.getText(),BAD_REQUEST);
        }
        else
            return new ResponseEntity<>(CANNOT_FOUND_ID.getText(), NOT_FOUND);
    }
}
