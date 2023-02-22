package study.movieservice.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import study.movieservice.domain.movie.Recommend;

import java.util.Optional;

@Mapper
public interface RecommendMapper {
    void reviewJoin(Recommend recommend);

    Optional<Recommend> findByReviewIdAndMemberId(Long reviewId, Long memberId);

    void reviewUpdate(@Param("recommend") Recommend recommend,@Param("memberId") Long memberId);

    void deleteRecommend(Long memberId, Long reviewId);
}
