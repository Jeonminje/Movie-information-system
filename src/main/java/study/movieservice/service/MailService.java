package study.movieservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import study.movieservice.domain.Member;
import study.movieservice.mail.MailForm;
import org.springframework.mail.javamail.JavaMailSender;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;
    private final String address;
    private final String sender;

    public MailService(JavaMailSender javaMailSender, @Value("{spring.mail.username}") String address,
                       @Value("{spring.mail.sendername") String sender) {
        this.javaMailSender = javaMailSender;
        this.address = address;
        this.sender=sender;
    }

    public boolean sendEmail(Member member, String mailSubject) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            helper.setSubject(mailSubject);
            helper.setFrom(address, sender);
            helper.setTo(member.getEmail());
            helper.setText(MailForm.contentText(member, mailSubject), true);

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new MailSendException("이메일 전송 실패");
        }
        return true;
    }
}