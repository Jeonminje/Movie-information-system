package study.movieservice.domain.movie;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MovieInfo {
    private final Movie movie;
    private final Double ratingAverage;
    private final Integer ratingCnt;
    private final List<RatingVO> data;
}
