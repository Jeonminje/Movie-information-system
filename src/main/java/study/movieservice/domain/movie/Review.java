package study.movieservice.domain.movie;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
