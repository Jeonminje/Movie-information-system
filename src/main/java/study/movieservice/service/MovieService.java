package study.movieservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import study.movieservice.domain.PagingVO;
import study.movieservice.domain.SessionConst;
import study.movieservice.domain.movie.Movie;
import study.movieservice.domain.movie.Review;
import study.movieservice.domain.movie.ReviewVO;
import study.movieservice.repository.MovieMapper;
import study.movieservice.repository.ReviewMapper;

import javax.servlet.http.HttpSession;
import java.util.List;

import static study.movieservice.domain.ExceptionMessageConst.FAILED_BRING_REVIEW;

@Service
public class MovieService {

    private final ReviewMapper reviewMapper;
    private final HttpSession httpSession;
    private final MovieMapper movieMapper;
    private final Integer reviewPerPage;

    public MovieService(ReviewMapper reviewMapper, HttpSession httpSession, MovieMapper movieMapper,
                        @Value("${reviewPerPage}") Integer reviewPerPage) {
        this.reviewMapper = reviewMapper;
        this.httpSession = httpSession;
        this.movieMapper = movieMapper;
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

    public PagingVO getReviewList(Integer currentPageNum){

        int total;
        int totalPageNum;
        int startIdx = (currentPageNum - 1) * reviewPerPage;
        List<ReviewVO> data;

        try{
            total = reviewMapper.getTotalRowCount();
            data = reviewMapper.getReviewList(startIdx);
            totalPageNum = (int) Math.ceil((double) total/reviewPerPage);
        } catch (Exception e){
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

    public void addMovie(Movie movie){
        movieMapper.save(movie);
    }
}
