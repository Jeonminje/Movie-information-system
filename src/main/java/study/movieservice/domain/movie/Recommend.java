package study.movieservice.domain.movie;

import lombok.Builder;
import lombok.Getter;

/**
 * 추천 정보를 담는 DTO
 */
@Getter
@Builder
public class Recommend {
    private final Long recommendId;
    private final Long reviewId;
    private final Long memberId;
    private final Boolean recommendState;
}
