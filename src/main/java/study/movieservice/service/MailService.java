package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import study.movieservice.domain.Member;
import study.movieservice.mail.MailHandler;
import study.movieservice.repository.mybatis.MemberMapper;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MemberMapper memberMapper;
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String address;

    public void sendEmailAuth(Member member){
        try {
            MailHandler sendMail = new MailHandler(javaMailSender);
            sendMail.setSubject("[이메일 인증메일 입니다.]"); //메일제목
            sendMail.setText(
                    "<br>아래 [이메일 인증 확인]을 눌러주세요." +
                            "<br><a href='http://localhost:8080/members/confirm-email?loginId="+member.getLoginId()+"&email=" + member.getEmail() +
                            "&email_key=" + member.getEmailKey() +
                            "' target='_blank'>이메일 인증 확인</a>");
            sendMail.setFrom(address, "movieservice");
            sendMail.setTo(member.getEmail());
            sendMail.send();
        } catch (Exception e){
            throw new MailSendException("이메일 전송 실패");
        }
    }

    public void updateMailAuth(Member member,String email,String emailKey){
        if(member.getEmailKey().equals(emailKey)&&member.getEmail().equals(email))
            memberMapper.updateMailAuth(member);
    }
}
