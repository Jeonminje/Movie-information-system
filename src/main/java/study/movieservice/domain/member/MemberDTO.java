package study.movieservice.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;

/**
 * input으로 받은 memberDTO
 * 각 element들의 유효성 검사를 진행
 * 
 */
@Getter
@RequiredArgsConstructor
public class MemberDTO {
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$")
    private final String email;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9]{4,9}")
    private final String loginId;

    @NotBlank
    @Size(min = 2, max = 10)
    private final String loginPassword;

    @NotBlank
    @Size(min = 2)
    private final String nickname;
}
