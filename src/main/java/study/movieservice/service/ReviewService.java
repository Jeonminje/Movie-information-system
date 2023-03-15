package study.movieservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import study.movieservice.domain.PagingVO;
import study.movieservice.domain.SessionConst;
import study.movieservice.domain.movie.Review;
import study.movieservice.domain.movie.ReviewVO;
import study.movieservice.repository.ReviewMapper;

import javax.servlet.http.HttpSession;
import java.util.List;

import static study.movieservice.domain.ExceptionMessageConst.FAILED_BRING_REVIEW;
import static study.movieservice.domain.ExceptionMessageConst.FAILED_DELETE_REVIEW;

@Service
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final HttpSession httpSession;
    private final Integer reviewPerPage;

    public ReviewService(ReviewMapper reviewMapper, HttpSession httpSession,
                        @Value("${reviewPerPage}") Integer reviewPerPage) {
        this.reviewMapper = reviewMapper;
        this.httpSession = httpSession;
        this.reviewPerPage = reviewPerPage;
    }

    public void saveReview(Review reviewDto) {
        Long memberId = (Long) httpSession.getAttribute(SessionConst.MEMBER_ID);

        Review review = Review.builder()
                .movieId(reviewDto.getMovieId())
                .memberId(memberId)
                .content(reviewDto.getContent())
                .likeCount(0L)
                .disLikeCount(0L)
                .rating(reviewDto.getRating()).build();
        reviewMapper.save(review);
    }

    public void deleteReview(Long reviewId) {
        try{
            reviewMapper.delete(reviewId);
        } catch (DataAccessException e){
            throw new IllegalArgumentException(FAILED_DELETE_REVIEW.getMessage());
        }

    }

    public PagingVO getReviewList(Integer currentPageNum){

        int total;
        int totalPageNum;
        int startIdx = (currentPageNum - 1) * reviewPerPage;
        List<ReviewVO> data;

        try{
            total = reviewMapper.getTotalRowCount();
            data = reviewMapper.getReviewList(startIdx);
            totalPageNum = (int) Math.ceil((double) total/reviewPerPage);
        } catch (DataAccessException e){
            throw  new IllegalArgumentException(FAILED_BRING_REVIEW.getMessage());
        }

        PagingVO result = PagingVO.builder()
                .total(total)
                .totalPageNum(totalPageNum)
                .currentPage(currentPageNum)
                .data(data)
                .build();

        return result;
    }
    public void increaseLikeCount(Long reviewId){
        reviewMapper.increaseLikeCount(reviewId);
    }

    public void increaseDislikeCount(Long reviewId){
        reviewMapper.increaseDislikeCount(reviewId);
    }

    public void decreaseLikeCount(Long reviewId){
        reviewMapper.decreaseLikeCount(reviewId);
    }
    public void decreaseDislikeCount(Long reviewId){
        reviewMapper.decreaseDislikeCount(reviewId);
    }
    public Long getLikeCount(Long reviewId){
        return reviewMapper.getLikeCount(reviewId);
    }
}
