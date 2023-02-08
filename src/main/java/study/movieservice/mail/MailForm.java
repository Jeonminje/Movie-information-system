package study.movieservice.mail;

import study.movieservice.domain.Member;

public final class MailForm {

    public static String contentText(Member member,String mailSubject) {

        StringBuffer buffer = new StringBuffer();

        if(mailSubject.equals("회원가입인증")) {
            buffer.append("<br>아래 [이메일 인증 확인]을 눌러주세요.");
            buffer.append("<br><a href='http://localhost:8080/members/confirm-email?");
            buffer.append("loginId=");
            buffer.append(member.getLoginId());
            buffer.append("&email=");
            buffer.append(member.getEmail());
            buffer.append("&emailKey=");
            buffer.append(member.getEmailKey());
            buffer.append("' target='_blank'>이메일 인증 확인</a>");
        }

        String result = buffer.toString();
        return result;
    }

    public static String senderName() {
        return "movieService 운영자";
    }
}
