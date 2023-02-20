package study.movieservice.repository;

import org.apache.ibatis.annotations.Mapper;
import study.movieservice.domain.movie.Recommend;

import java.util.Optional;

@Mapper
public interface RecommendMapper {
    void reviewJoin(Recommend recommend);

    Optional<Recommend> findByReviewIdAndMemberId(Long memberId, Long reviewId);

    void reviewUpdate(Recommend recommend);

    void deleteRecommend(Long memberId, Long reviewId);
}
