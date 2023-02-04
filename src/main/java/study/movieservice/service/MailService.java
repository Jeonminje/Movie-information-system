package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailSender {

    private final JavaMailSender emailSender;
    private Integer certificationNumber;

    public MimeMessage createMessage(String email) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, email);// 보내는 대상
        message.setSubject("Movie Systems 회원가입 이메일 인증입니다.");// 제목

        String content =
                "Movie Systems 회원가입 인증 메일입니다." +
                        "<br><br>" +
                        "인증 번호는 " + certificationNumber + "입니다." +
                        "<br>" +
                        "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
        message.setText(content, "utf-8", "html");// 내용, charset 타입, subtype

        // 발신자의 이메일 주소, 발신자 이름
        message.setFrom(new InternetAddress("practice450@naver.com", "MovieSystems"));// 보내는 사람

        return message;
    }

    public Integer createKey() {
        Random random = new Random();
        Integer randomNum = random.nextInt(888888) + 111111;

        return randomNum;
    }

    public Integer sendMail(String email) throws Exception {
        certificationNumber = createKey();

        MimeMessage message = createMessage(email);

        try {
            emailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }

        return certificationNumber; // 메일로 보낸 인증 코드를 서버로 반환
    }
}
