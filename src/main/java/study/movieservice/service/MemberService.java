package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import study.movieservice.domain.Member;
import study.movieservice.mail.TempKey;
import study.movieservice.repository.mybatis.MemberMapper;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

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
}