package study.movieservice.domain.movie;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 영화 클릭 시, 영화에 대한 각종 정보를 반환해주는 VO.
 */
@Getter
@Builder
public class MovieInfo {
    private final Movie movie;
    private final Double ratingAverage;
    private final Integer ratingCnt;
    private final List<RatingVO> data;
}
