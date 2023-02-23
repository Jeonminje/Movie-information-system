package study.movieservice.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import study.movieservice.domain.movie.Recommend;

import java.util.Optional;

@Mapper
public interface RecommendMapper {
    void recommendSave(Recommend recommend);

    boolean findByReviewIdAndMemberId(Long reviewId, Long memberId);

    void recommendUpdate(@Param("recommend") Recommend recommend,@Param("memberId") Long memberId);

    void recommendDelete(Long memberId, Long reviewId);
}
