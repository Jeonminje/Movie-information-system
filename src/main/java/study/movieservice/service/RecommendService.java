package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.movieservice.controller.aop.annotation.RedissonLock;
import study.movieservice.domain.movie.Recommend;
import study.movieservice.repository.RecommendMapper;

import static study.movieservice.domain.ExceptionMessageConst.*;

/**
 * recommend와 관련된 비지니스 로직을 처리하는 클래스
 */
@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendMapper recommendMapper;
    private final ReviewService reviewService;
    private final SessionManager sessionManager;

    /**
     * 추천입력을 받았을때 recommend 테이블에 객체를 등록하고 해당 review의 likeCount를 1증가시키는 메소드
     *
     * @param inputRecommend 어떤 리뷰인지 알려주는 reviewId와
     *                       1일때 추천, 0일때 비추천을 나타내는 recommendState 정보보가담겨있는 Recommend 객체
     * @throws IllegalArgumentException 만약 reviewId, memberId가 일치하는 객체가 DB에 없을때 발생
     */
    @RedissonLock(key="reviewId")
    public void recommendSave(Recommend inputRecommend) {

        Long memberId = sessionManager.getMemberId();
        Long reviewId=inputRecommend.getReviewId();

        if (recommendMapper.findByReviewIdAndMemberId(reviewId, memberId))
            throw new IllegalArgumentException(FAILED_RECOMMEND_REQUEST.getMessage());

        Recommend recommend = Recommend.builder()
                .memberId(memberId)
                .reviewId(inputRecommend.getReviewId())
                .recommendState(inputRecommend.getRecommendState())
                .build();

        recommendMapper.recommendSave(recommend);
        reviewService.increaseLikeCount(reviewId);
    }

    /**
     * 추천 수정을 입력받았을때 recommend 테이블의 해당객체와 review 테이블의 likeCount와 dislikeCount를 수정하는 메소드
     *
     *
     * @param inputRecommend 어떤 리뷰인지 알려주는 reviewId와
     *                       1일때 추천, 0일때 비추천을 나타내는 recommendState 정보보가담겨있는 Recommend 객체
     * @throws IllegalArgumentException 만약 reviewId, memberId가 일치하는 객체가 DB에 없을때 발생
     */
    @RedissonLock(key="reviewId")
    public void recommendUpdate(Recommend inputRecommend) {

        Long memberId = sessionManager.getMemberId();
        Long reviewId= inputRecommend.getReviewId();

        if (!recommendMapper.findByReviewIdAndMemberId(reviewId, memberId))
            throw new IllegalArgumentException(FAILED_RECOMMEND_REQUEST.getMessage());

        if(inputRecommend.getRecommendState()) {
            reviewService.increaseLikeCount(reviewId);
            reviewService.decreaseDislikeCount(reviewId);
        }
        else{
            reviewService.increaseDislikeCount(reviewId);
            reviewService.decreaseLikeCount(reviewId);
        }
        recommendMapper.recommendUpdate(inputRecommend, memberId);
    }

    /**
     * 추천 삭제를 요청받았을때 review 테이블의 해당객체를 삭제하는 메소드
     *
     * @param inputRecommend 어떤 리뷰인지 알려주는 reviewId와
     *                       1일때 추천, 0일때 비추천을 나타내는 recommendState 정보보가담겨있는 Recommend 객체
     * @throws IllegalArgumentException 만약 reviewId, memberId가 일치하는 객체가 DB에 없을때 발생
     */
    @RedissonLock(key="reviewId")
    public void recommendDelete(Recommend inputRecommend) {

        Long memberId = sessionManager.getMemberId();
        Long reviewId= inputRecommend.getReviewId();

        if (!recommendMapper.findByReviewIdAndMemberId(reviewId, memberId))
            throw new IllegalArgumentException(FAILED_RECOMMEND_REQUEST.getMessage());
        recommendMapper.recommendDelete(memberId, reviewId);
    }
}
