package study.movieservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import study.movieservice.domain.PagingVO;
import study.movieservice.domain.SessionConst;
import study.movieservice.domain.member.Grade;
import study.movieservice.domain.movie.Review;
import study.movieservice.domain.movie.ReviewVO;
import study.movieservice.repository.MemberMapper;
import study.movieservice.repository.ReviewMapper;

import javax.servlet.http.HttpSession;
import java.util.List;

import static study.movieservice.domain.movie.MovieListType.ALL;

@Service
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final HttpSession httpSession;
    private final Integer reviewPerPage;
    private final MemberMapper memberMapper;

    public ReviewService(ReviewMapper reviewMapper, HttpSession httpSession,
                         @Value("${reviewPerPage}") Integer reviewPerPage, MemberMapper memberMapper) {
        this.reviewMapper = reviewMapper;
        this.httpSession = httpSession;
        this.reviewPerPage = reviewPerPage;
        this.memberMapper = memberMapper;
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
    public void checkGrade(){
        Long memberId = (Long) httpSession.getAttribute(SessionConst.MEMBER_ID);

        int reviewCount=reviewMapper.getReviewCount(memberId);

        if(reviewCount>5)
            memberMapper.changeGrade(memberId, Grade.VIP);
        else
            memberMapper.changeGrade(memberId,Grade.BASIC);
    }
}
