package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import study.movieservice.domain.Member;
import study.movieservice.domain.ResultEnum;
import study.movieservice.domain.SessionKey;
import study.movieservice.exception.custom.NotCompleteAuthException;
import study.movieservice.exception.custom.NotFoundMemberException;
import study.movieservice.mail.TempKey;
import study.movieservice.repository.mybatis.MemberMapper;

import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import java.util.Optional;

import static study.movieservice.domain.ResultEnum.*;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberService memberService;
    private final HttpSession session;

    public boolean loginAccess(Member compareMember) {//아이디랑 비밀번호만 온 상태
        String compareId = compareMember.getLoginId();
        String comparePw = compareMember.getLoginPassword();

        // 멤버 존재 여부
        // 인증 여부
        // 로그인 여부
        Optional<Member> receiver = memberService.findByLoginId(compareId);

        if (receiver.isEmpty())
            throw new NotFoundMemberException(CANNOT_FOUND_ID);

        if (!memberService.checkEmailAuth(compareId))
            throw new NotCompleteAuthException(NOT_COMPLETED_AUTH);

        String realPassword = memberService.findPasswordByLoginId(compareId);

        if (BCrypt.checkpw(comparePw, realPassword)) {
            Member userInfo = memberService.loadUserInfo(compareId);

            session.setAttribute(SessionKey.SESSION_LOGIN_ID, userInfo.getLoginId());

            return true;
        }
        return false;
    }

    public void logout() {
        session.invalidate();
    }
}