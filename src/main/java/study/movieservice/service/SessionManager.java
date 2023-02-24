package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.movieservice.domain.SessionConst;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionManager {

    private final HttpSession session;

    public void storeLoginIdAndNickname(Long memberId){
        session.setAttribute(SessionConst.MEMBER_ID, memberId);
    }

    public void deleteAll(){
        session.invalidate();
    }

    public Long getMemberId(){
        return (Long) session.getAttribute(SessionConst.MEMBER_ID);
    }
}
