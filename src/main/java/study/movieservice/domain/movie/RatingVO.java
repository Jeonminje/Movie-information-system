package study.movieservice.domain.movie;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 특정 평점의 개수를 담는 VO
 */
@Getter
@RequiredArgsConstructor
public class RatingVO {
    private final Double rating;
    private final Integer count;
}
