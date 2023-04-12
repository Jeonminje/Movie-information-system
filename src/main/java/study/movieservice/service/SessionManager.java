package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.movieservice.domain.SessionConst;

import javax.servlet.http.HttpSession;

/**
 * HTTP 세션 관리 클래스
 */
@Service
@RequiredArgsConstructor
public class SessionManager {
    /**
     * spring bean 자동 주입 필드
     */
    private final HttpSession session;

    /**
     * 로그인 시, 세션에 현재 로그인 정보 저장
     * @param memberId
     */
    public void storeLoginId(Long memberId){
        session.setAttribute(SessionConst.MEMBER_ID, memberId);
    }

    /**
     * 로그 아웃시, 세션 비움
     */
    public void deleteAll(){
        session.invalidate();
    }

    /**
     * @return 세션에 있는 로그인 아이디 반환
     */
    public Long getMemberId(){
        return (Long) session.getAttribute(SessionConst.MEMBER_ID);
    }
}
