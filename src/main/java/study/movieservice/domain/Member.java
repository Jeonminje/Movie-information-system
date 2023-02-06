package study.movieservice.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Member {
    private Long id;
    private String loginId;
    private String email;
    private String loginPassword;
    private String nickname;
    private Grade grade;
    private String emailKey;
}
