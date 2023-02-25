package study.movieservice.repository;

import org.apache.ibatis.annotations.Mapper;
import study.movieservice.domain.movie.Review;
import study.movieservice.domain.movie.ReviewVO;

import java.util.List;

@Mapper
public interface ReviewMapper {
    void save(Review review);
    void delete(Long reviewId);
    int getTotalRowCount();
    List<ReviewVO> getReviewList(int startIdx);

    void increaseLikeCount(Long reviewId);

    void increaseDislikeCount(Long reviewId);

    void decreaseLikeCount(Long reviewId);

    void decreaseDislikeCount(Long reviewId);

    Long getLikeCount(Long reviewId);
}
