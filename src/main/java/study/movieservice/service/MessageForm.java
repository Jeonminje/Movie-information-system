package study.movieservice.service;

public final class MessageForm {

    private MessageForm(){
    }

    public static String signUpMessageSubject() {
        return "Movie Systems 회원가입 이메일 인증입니다.";
    }

    public static String signUpMessageContent(String emailCode) {
        return "Movie Systems 회원가입 인증 메일입니다." +
                "<br><br>" +
                "인증 번호는 " + emailCode + "입니다." +
                "<br>" +
                "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
    }
}
