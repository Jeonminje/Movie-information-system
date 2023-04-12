package study.movieservice.repository;

import org.apache.ibatis.annotations.Mapper;
import study.movieservice.domain.movie.RatingVO;
import study.movieservice.domain.movie.Review;
import study.movieservice.domain.movie.ReviewVO;

import java.util.List;

/**
 * review 테이블에 접근하는 매퍼.
 */
@Mapper
public interface ReviewMapper {
    void save(Review review);
    void delete(Long reviewId);
    int getTotalRowCount();
    List<ReviewVO> getReviewList(Long movieId, int startIdx);
    List<RatingVO> getRatingList(Long movieId);
    Integer getRowCount(Long movieId);
    Double getRatingAverage(Long movieId);
    void increaseLikeCount(Long reviewId);
    void increaseDislikeCount(Long reviewId);
    void decreaseLikeCount(Long reviewId);
    void decreaseDislikeCount(Long reviewId);
    Long getLikeCount(Long reviewId);
    int getReviewCount(Long memberId);
}
