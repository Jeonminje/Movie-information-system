package study.movieservice.repository;

import org.apache.ibatis.annotations.Mapper;
import study.movieservice.domain.movie.RatingVO;
import study.movieservice.domain.movie.Review;
import study.movieservice.domain.movie.ReviewVO;

import java.util.List;

@Mapper
public interface ReviewMapper {
    void save(Review review);
    void delete(Long reviewId);
    int getTotalRowCount();
    List<ReviewVO> getReviewList(int startIdx);
    List<RatingVO> getRatingList(Long movieId);
    Integer getRowCount(Long movieId);
    Double getRatingAverage(Long movieId);
}
