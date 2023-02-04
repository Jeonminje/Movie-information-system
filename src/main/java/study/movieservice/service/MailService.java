package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.movieservice.repository.RedisUtil;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;
    private final RedisUtil redisUtil;

    public MimeMessage createMessage(String email, String emailCode) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject(messageSubject());

        String content = messageContent(emailCode);
        message.setText(content, "utf-8", "html");


        message.setFrom(new InternetAddress("practice450@naver.com","MovieSystems"));

        return message;
    }

    private String messageSubject() {
        return "Movie Systems 회원가입 이메일 인증입니다.";
    }

    private String messageContent(String emailCode) {
        return "Movie Systems 회원가입 인증 메일입니다." +
                "<br><br>" +
                "인증 번호는 " + emailCode + "입니다." +
                "<br>" +
                "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
    }

    public String createKey() {
        StringBuilder key = new StringBuilder("");
        Random random = new Random();

        for(int i=0;i<6;i++){
            Integer randomNum = random.nextInt(9);
            key.append(randomNum);
        }

        return key.toString();
    }

    @Transactional
    public void sendMail(String email) throws Exception {
        String emailCode = createKey();
        MimeMessage message = createMessage(email, emailCode);

        redisUtil.setDataExpire(email, emailCode, 60*3L);
        emailSender.send(message);
    }

    public boolean checkEmailCode(String email, String emailCode){
        String code = redisUtil.getData(email).get();

        if( code != null && code.equals(emailCode)){
            redisUtil.deleteData(email);
            return true;
        }else{
            return false;
        }
    }
}
