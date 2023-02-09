package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.movieservice.domain.Member;
import study.movieservice.service.MailService;
import study.movieservice.service.MemberService;
import java.util.Map;
import java.util.Optional;
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
            return new ResponseEntity<>(SUCCESS_CAN_USE.getText(), SUCCESS_CAN_USE.getStatus());
        }
        return new ResponseEntity<>(FAILED_CAN_USE.getText(), FAILED_CAN_USE.getStatus());
    }

    @PostMapping("/email-auth")
    public ResponseEntity<String> emailAuth(@RequestBody Member member) {
        Optional<Member> receiver = memberService.findByLoginId(member.getLoginId());

        if (receiver.isPresent()) {
            if (mailService.sendEmail(receiver.get(),"회원가입인증"))
                return new ResponseEntity<>(SUCCESS_SENDMAIL.getText(), SUCCESS_SENDMAIL.getStatus());
            else
                return new ResponseEntity<>(FAILED_SENDMAIL.getText(), FAILED_SENDMAIL.getStatus());
        }
        else
            return new ResponseEntity<>(CANNOT_FOUND_ID.getText(), CANNOT_FOUND_ID.getStatus());
    }

    @GetMapping("/confirm-email")
    public ResponseEntity<String> confirmEmail(@RequestParam String loginId, @RequestParam String email, @RequestParam String emailKey) {
        Optional<Member> receiver = memberService.findByLoginId(loginId);

        if (receiver.isPresent()) {
            if (memberService.updateMailAuth(receiver.get(), email, emailKey))
                return new ResponseEntity<>(SUCCESS_AUTH.getText(), SUCCESS_CAN_USE.getStatus());
            else
                return new ResponseEntity<>(FAILED_AUTH.getText(),FAILED_AUTH.getStatus());
        }
        else
            return new ResponseEntity<>(CANNOT_FOUND_ID.getText(), CANNOT_FOUND_ID.getStatus());
    }
}