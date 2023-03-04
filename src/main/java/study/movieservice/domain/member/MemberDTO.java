package study.movieservice.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
public class MemberDTO {
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$")
    private final String email;

    @NotBlank
    private final String loginId;

    @NotBlank
    @Size(min=2,max=10)
    private final String loginPassword;

    @NotBlank
    private final String nickname;

}
