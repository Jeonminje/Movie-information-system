package study.movieservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import study.movieservice.domain.PagingVO;
import study.movieservice.domain.SessionConst;
import study.movieservice.domain.movie.Review;
import study.movieservice.domain.movie.ReviewVO;
import study.movieservice.repository.ReviewMapper;

import javax.servlet.http.HttpSession;
import java.util.List;

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
                .rating(reviewDto.getRating()).build();
        reviewMapper.save(review);
    }

    public void deleteReview(Long reviewId) {

        reviewMapper.delete(reviewId);
    }

    public PagingVO getReviewList(Integer currentPageNum){

        int total = reviewMapper.getTotalRowCount();
        int totalPageNum = (int) Math.ceil((double) total/reviewPerPage);
        int startIdx = (currentPageNum - 1) * reviewPerPage;
        List<ReviewVO> data = reviewMapper.getReviewList(startIdx);

        PagingVO result = PagingVO.builder()
                .total(total)
                .totalPageNum(totalPageNum)
                .currentPage(currentPageNum)
                .data(data)
                .build();

        return result;
    }
}
