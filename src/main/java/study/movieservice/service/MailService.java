package study.movieservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import study.movieservice.domain.Member;
import study.movieservice.mail.MailForm;
import study.movieservice.mail.TempKey;
import study.movieservice.repository.mybatis.MemberMapper;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    private final MemberMapper memberMapper;
    private final JavaMailSender javaMailSender;
    private final String address;

    public MailService(MemberMapper memberMapper, JavaMailSender javaMailSender, @Value("{spring.mail.username}") String address) {
        this.memberMapper = memberMapper;
        this.javaMailSender = javaMailSender;
        this.address = address;
    }

    public boolean sendEmail(Member member, String mailSubject) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            helper.setSubject(mailSubject);
            helper.setFrom(address, MailForm.senderName());
            helper.setTo(member.getEmail());
            helper.setText(MailForm.contentText(member, mailSubject), true);

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new MailSendException("이메일 전송 실패");
        }
        return true;
    }

    public boolean updateMailAuth(Member member, String email, String emailKey) {
        if (member.getEmailKey().equals(emailKey) && member.getEmail().equals(email)) {
            String newMailKey = new TempKey().getKey(8, false);

            memberMapper.updateMailAuth(member);
            memberMapper.updateMailKey(member, newMailKey);

            return true;
        }
        else
            return false;
    }
}
