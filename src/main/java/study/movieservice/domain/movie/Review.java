package study.movieservice.domain.movie;

import lombok.Builder;
import lombok.Getter;

/**
 * 리뷰 정보를 담는 DTO
 */
@Getter
@Builder
public class Review {
    private final Long reviewId;
    private final Long movieId;
    private final Long memberId;
    private final String content;
    private final Double rating;
    private final Long likeCount;
    private final Long disLikeCount;
}
