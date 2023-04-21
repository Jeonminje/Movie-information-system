package study.movieservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.Duration;
import java.util.Random;

import static study.movieservice.domain.ExceptionMessageConst.*;

/**
 * 메일 관련 로직 클래스.
 */
@Service
public class MailService {
    /**
     * email 인증코드 전송을 위해 사용하는 객체, 스프링 자동 주입
     */
    private final JavaMailSender emailSender;
    /**
     * 전송된 인증코드를 일정시간 저장하기 위해 사용.
     */
    private final StringRedisTemplate redisTemplate;
    /**
     * 이메일 발신자 정보
     */
    private final String address;
    private final String sender;

    public MailService(JavaMailSender emailSender, StringRedisTemplate redisTemplate,
                       @Value("${spring.mail.username}") String address,
                       @Value("${sender}") String sender) {
        this.emailSender = emailSender;
        this.redisTemplate = redisTemplate;
        this.address = address;
        this.sender = sender;
    }

    /**
     * 회원가입 인증 메일 생성 함수
     * @param email 이메일 주소
     * @param emailCode 생성된 인증 코드
     * @return 보낼 내용을 담은 MimeMessage 객체
     */
    public MimeMessage createSignUpMessage(String email, String emailCode)  {

        MimeMessage message = emailSender.createMimeMessage();

        try{
            message.addRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject(MessageForm.signUpMessageSubject());

            String content = MessageForm.signUpMessageContent(emailCode);
            message.setText(content, "utf-8", "html");
            message.setFrom(new InternetAddress(address, sender));
        } catch (Exception e) {
            throw new MailSendException(FAILED_WRITE_EMAIL.getMessage());
        }

        storeKey(email, emailCode);

        return message;
    }

    /**
     * 인증코드를 redis에 저장하는 함수
     * @param email 이메일 주소
     * @param emailCode 생성된 인증코드
     */
    private void storeKey(String email, String emailCode) {
        ValueOperations<String, String> store = redisTemplate.opsForValue();
        Duration duration = Duration.ofSeconds(60*3L);
        store.set(email, emailCode, duration);
    }

    /**
     * 인증코드 생성 함수
     * @return 인증코드
     */
    public String createKey() {
        StringBuilder key = new StringBuilder("");
        Random random = new Random();

        for(int i=0;i<6;i++){
            Integer randomNum = random.nextInt(9);
            key.append(randomNum);
        }

        return key.toString();
    }

    /**
     * 메일 전송 함수
     * @param message
     */
    public void sendMail(MimeMessage message) {
        try{
            emailSender.send(message);
        }catch (Exception e){
            throw new MailSendException(FAILED_SEND_EMAIL.getMessage());
        }
    }

    /**
     * 인증코드가 일치하는지 확인하는 함수
     * @param email 이메일 주소
     * @param emailCode 인증코드
     */
    public void checkEmailCode(String email, String emailCode){
        ValueOperations<String,String > store = redisTemplate.opsForValue();
        String code = store.get(email);

        if(code != null && code.equals(emailCode)){
            redisTemplate.delete(email);
        }else{
            throw new MailAuthenticationException(FAILED_CHECK_EMAIL.getMessage());
        }
    }
}
