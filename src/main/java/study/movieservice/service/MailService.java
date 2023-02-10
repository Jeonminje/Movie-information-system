package study.movieservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.Duration;
import java.util.Random;

@Service
public class MailService {

    private final JavaMailSender emailSender;
    private final StringRedisTemplate redisTemplate;
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

    public MimeMessage createSignUpMessage(String email, String emailCode)  {

        MimeMessage message = emailSender.createMimeMessage();

        try{
            message.addRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject(MessageForm.signUpMessageSubject());

            String content = MessageForm.signUpMessageContent(emailCode);
            message.setText(content, "utf-8", "html");
            message.setFrom(new InternetAddress(address, sender));
        } catch (Exception e) {
            throw new MailSendException("이메일 작성에 실패하였습니다.");
        }

        storeKey(email, emailCode);

        return message;
    }

    private void storeKey(String email, String emailCode) {
        ValueOperations<String, String> store = redisTemplate.opsForValue();
        Duration duration = Duration.ofSeconds(60*3L);
        store.set(email, emailCode, duration);
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

    public void sendMail(MimeMessage message) {
        try{
            emailSender.send(message);
        }catch (Exception e){
            throw new MailSendException("이메일 전송에 실패하였습니다.");
        }
    }

    public boolean checkEmailCode(String email, String emailCode){
        ValueOperations<String,String > store = redisTemplate.opsForValue();
        String code = store.get(email);

        if(code != null && code.equals(emailCode)){
            redisTemplate.delete(email);
            return true;
        }else{
            return false;
        }
    }
}
