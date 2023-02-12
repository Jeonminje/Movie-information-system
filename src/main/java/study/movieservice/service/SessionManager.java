package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.movieservice.domain.SessionConst;
import study.movieservice.exception.LoginSetAttributeException;
import study.movieservice.exception.LogoutException;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionManager {

    private final HttpSession session;

    public void storeLoginIdAndNickname(String loginId, String nickname){
        try{
            session.setAttribute(SessionConst.LOGIN_ID, loginId);
            session.setAttribute(SessionConst.NICKNAME, nickname);
        } catch (Exception e){
            throw new LoginSetAttributeException();
        }

    }

    public void deleteAll(){
        try{
            session.invalidate();
        }catch (Exception e){
            throw new LogoutException();
        }
    }
}
