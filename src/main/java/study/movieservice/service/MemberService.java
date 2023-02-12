package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import study.movieservice.domain.Member;
import study.movieservice.domain.SessionKey;
import study.movieservice.mail.TempKey;
import study.movieservice.repository.mybatis.MemberMapper;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final HttpSession httpSession;

    public void save(Member member) {

        String tempKey = new TempKey().getKey(8, false);

        Member encryptMember = Member.builder()
                .loginId(member.getLoginId())
                .loginPassword(BCrypt.hashpw(member.getLoginPassword(), BCrypt.gensalt()))
                .email(member.getEmail())
                .nickname(member.getNickname())
                .emailKey(tempKey)
                .build();

        memberMapper.save(encryptMember);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return memberMapper.findByLoginId(loginId);
    }

    public boolean updateMailAuth(Member member, String email, String emailKey) {
        if (member.getEmailKey().equals(emailKey) && member.getEmail().equals(email)) {
            String newMailKey = new TempKey().getKey(8, false);

            memberMapper.updateMailAuth(member);
            memberMapper.updateMailKey(member, newMailKey);

            return true;
        }
        return false;
    }

    public int checkEmailAuth(String loginId){
        return memberMapper.checkEmailAuth(loginId);
    }

    public boolean loginAccess(Member compareMember){//아이디랑 비밀번호만 온 상태
        String compareId=compareMember.getLoginId();
        String comparePw=compareMember.getLoginPassword();
        String realPassword=memberMapper.findPasswordByLoginId(compareId);

        if(BCrypt.checkpw(comparePw,realPassword)) {
            Member userInfo=memberMapper.loadUserInfo(compareId);

            httpSession.setAttribute(SessionKey.SESSION_LOGIN_ID,userInfo.getLoginId());

            return true;
        }
        return false;
    }

    public void logout(){
        httpSession.invalidate();
    }
}