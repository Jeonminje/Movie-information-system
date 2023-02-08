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
    public void join(@RequestBody Member member) {
        memberService.save(member);
    }

    @PostMapping("/id-check")
    public ResponseEntity<String> isIdDuplicated(@RequestBody Member member) {
        if (memberService.findByLoginId(member.getLoginId()).isEmpty()) {
            return new ResponseEntity<>("사용가능한 아이디입니다.", HttpStatus.OK);
        }
        return new ResponseEntity<>("중복된 아이디입니다. 다시 입력해주세요", HttpStatus.CONFLICT);
    }

    @PostMapping("/email-auth")
    public ResponseEntity<String> emailAuth(@RequestBody Member member) {
        Optional<Member> receiver = memberService.findByLoginId(member.getLoginId());

        if (receiver.isPresent()) {
            if (mailService.sendEmail(receiver.get(),"회원가입인증"))
                return new ResponseEntity<>("메일 전송에 성공하였습니다.", HttpStatus.OK);
            else
                return new ResponseEntity<>("메일 전송에 실패하였습니다.", HttpStatus.SERVICE_UNAVAILABLE);
        }
        else
            return new ResponseEntity<>("존재하지않는 아이디입니다.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/confirm-email")
    public ResponseEntity<String> confirmEmail(@RequestParam String loginId, @RequestParam String email, @RequestParam String emailKey) {
        Optional<Member> receiver = memberService.findByLoginId(loginId);

        if (receiver.isPresent()) {
            if (mailService.updateMailAuth(receiver.get(), email, emailKey))
                return new ResponseEntity<>("인증이 완료되었습니다.", HttpStatus.OK);
            else
                return new ResponseEntity<>("인증에 실패하였습니다. 메일 전송을 다시 시도해주세요", HttpStatus.BAD_REQUEST);
        }
        else
            return new ResponseEntity<>("존재하지않는 아이디입니다.", HttpStatus.NOT_FOUND);
    }
}
