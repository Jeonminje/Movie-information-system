package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.movieservice.domain.SessionConst;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionManager {

    private final HttpSession session;

    public void storeLoginIdAndNickname(Long memberId, String nickname){
        session.setAttribute(SessionConst.MEMBER_ID, memberId);
        session.setAttribute(SessionConst.NICKNAME, nickname);
    }

    public void deleteAll(){
        session.invalidate();
    }
}
