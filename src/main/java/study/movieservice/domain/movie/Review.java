package study.movieservice.domain.movie;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Review {
    private final Long reviewId;
    private final Long movieId;
    private final Long memberId;
    private final String content;
    private final Double rating;
}
