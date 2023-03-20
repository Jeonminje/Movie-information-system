package study.movieservice.domain.movie;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RatingVO {
    private final Double rating;
    private final Integer count;
}
